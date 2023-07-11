import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DBApp {

      Hashtable<String ,Table> tableNameRefrence;

    public static void main(String[]args)
    {

        String strTableName = "Student";
        DBApp dbApp = new DBApp( );
        Hashtable htblColNameType = new Hashtable( );
        htblColNameType.put("id", "java.lang.Integer");
        htblColNameType.put("name", "java.lang.String");
        htblColNameType.put("gpa", "java.lang.double");

        Hashtable htblColNameMin = new Hashtable( );
        htblColNameMin.put("id", "1");
        htblColNameMin.put("name", "a");
        htblColNameMin.put("gpa", "0.01");

        Hashtable htblColNameMax = new Hashtable( );
        htblColNameMax.put("id", "9999");
        htblColNameMax.put("name", "zzzz");
        htblColNameMax.put("gpa", "9.99");

        dbApp.createTable( strTableName, "id", htblColNameType,htblColNameMin,htblColNameMax );




        String strTableName1 = "Student2";
        Hashtable htblColNameType1 = new Hashtable( );
        htblColNameType1.put("id", "java.lang.Integer");
        htblColNameType1.put("name", "java.lang.String");
        htblColNameType1.put("gpa", "java.lang.double");

        Hashtable htblColNameMin1 = new Hashtable( );
        htblColNameMin1.put("id", "1");
        htblColNameMin1.put("name", "a");
        htblColNameMin1.put("gpa", "0.01");

        Hashtable htblColNameMax1 = new Hashtable( );
        htblColNameMax1.put("id", "9999");
        htblColNameMax1.put("name", "zzzz");
        htblColNameMax1.put("gpa", "9.99");

        dbApp.createTable( strTableName1, "id", htblColNameType1,htblColNameMin1,htblColNameMax1 );



    }





    public DBApp()
    {
          tableNameRefrence=new Hashtable<String, Table>();
          init();
    }








    //** required function
    public void init()
    {
        //can initialize here the max number of records a page can have
        //any other initializations can be here
        Page.setMaxSize(200);
    }










    //required function **
    public void createTable(String strTableName, String strClusteringKeyColumn,
                            Hashtable<String,String> htblColNameType,
                            Hashtable<String,String> htblColNameMin,
                            Hashtable<String,String> htblColNameMax )
    {
        //pass this method to the constructor of table
        Table t=new Table( strTableName,
                strClusteringKeyColumn,
                htblColNameType,
                htblColNameMin,
                htblColNameMax  );


       // System.out.println(Arrays.deepToString(htblColNameMax.entrySet().toArray()));

        //validate();
        File myObj = new File(strTableName);
       new CSVWriter( createCsv( strTableName,
                   strClusteringKeyColumn,
                   htblColNameType,
                   htblColNameMin,
                   htblColNameMax),

                myObj.getAbsolutePath() );

       tableNameRefrence.put(strTableName,t);


        //should also write it to the csv of the table?? yess

    }


    public String[][] createCsv(String strTableName, String strClusteringKeyColumn,
                Hashtable<String,String> htblColNameType,
                Hashtable<String,String> htblColNameMin,
                Hashtable<String,String> htblColNameMax )
    {


        int rowNum=htblColNameMax.size();
        rowNum++;
        String [][] arr=new String[rowNum][8];

        arr[0]=new String[] {"Table Name", "Column Name", "Column Type", "ClusteringKey", "IndexName","IndexType", "min", "max"};

        // Storing all entries of Hashtable
        // in a Set using entrySet() method

        Set <Map.Entry<String, String>> entrySet = htblColNameMax.entrySet();

        // Iterating through the Hashtable object
        // using for-each loop
        int k=1;
       // System.out.println(Arrays.deepToString(entrySet.toArray()));
        for (Map.Entry<String, String> entry : entrySet)
        {

            String key = entry.getKey();

            arr[k][0]=strTableName;  //first column of each row contains table name
            arr [k][1]=key; //second column of each row contains column name
            k++;
        }

        for(int i=1;i<rowNum;i++)
        {
            String columnName=arr[i][1];
            arr[i][2]=htblColNameType.get(columnName);

            if(columnName.equals(strClusteringKeyColumn))
            arr[i][3]="TRUE";
            else
                arr[i][3]="False";

            arr[i][4]="NO INDEX NOW ";
            arr[i][5]="NO INBDEX NOW";
            arr[i][6]=htblColNameMin.get(columnName);
            arr[i][7]=htblColNameMax.get(columnName);
        }

        return  arr;



    }









    //required function **
    public void insertIntoTable(String strTableName,
                                Hashtable<String,Object> htblColNameValue)
    {
        Table table=tableNameRefrence.get(strTableName); //i got the reference of the table from hashtable

        if(table!=null)
           table.insertIntoTable(htblColNameValue);
    }











    public static int getTypeof(Object o)
    {

        if( o instanceof Integer)
        {
            return 1;
        }
        if( o instanceof String)
        {
            return 2;
        }
        if( o instanceof Double)
        {
            return 3;
        }
        if( o instanceof Date)
        {
            return 4;
        }


        //should throw an error here
        return -1;


    }











}
