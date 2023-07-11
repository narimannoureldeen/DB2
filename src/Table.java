
import java.util.*;


public class Table {
    String tableName;
    String clusteringColumnName;
   public Vector<Page> pages;
//vector<> for paths  (maybe)


    public Table(String strTableName, String strClusteringKeyColumn,
                 Hashtable<String,String> htblColNameType,
                 Hashtable<String,String> htblColNameMin,
                 Hashtable<String,String> htblColNameMax )
    {
        //initialilze table name
        this.tableName=strTableName;

        //initialize table primary column name
        this.clusteringColumnName=strClusteringKeyColumn;

        //initialize the vector of pages that will page our table
        pages=new Vector<>();


    }




    public void insertIntoTable(Hashtable<String,Object> record)
    {
        // get primary key of the record based on table attribute so i know where will the record be inserted
        Object primaryKeyValue=record.get(clusteringColumnName);

        if(pages.size()>0) {
            //get the required pageId to insert the record into
            int pageId = getPageId(primaryKeyValue);

            //if page id is negative -1 means that is an error
            //otherwise just insert normally


            //get the page that i should insert the record into
            Page page = pages.get(pageId);
            //initialize the tuple
            Tuple tuple = new Tuple(record, tableName,clusteringColumnName);


            while(tuple!=null)
            {
                 tuple=page.insert(tuple);
                 pageId++;
                 if(pageId==pages.size())
                 {

                     break;
                 }
                 else
                     page=pages.get(pageId);

                // if temp == null then insertion is done otherwise insertion need to happen in the page that is returned
            }

        }
        else
        {

        }






    }



    public int getPageId(Object primaryKeyValue)
    {

        int type=getTypeof(primaryKeyValue);  //returns an integer that indiactes the type of object

        //for each type we have a specific method
        switch (type)
        {
            case 1:return searchByIntger((Integer)primaryKeyValue);


            case 2:return searchByString((String) primaryKeyValue);


            case 3:return searchByDouble((Double) primaryKeyValue);


            case 4:return searchByDate((Date) primaryKeyValue);



            default:return  -1;

        }


    }



    //all the search methods just search on by the specific clusteringColumn type
    private int searchByIntger(Integer primaryKeyValue) {
        //binary search code
        int high=pages.size()-1;
        int low=0 ;

        while(low<=high)
        {
            int mid=(low+high)/2;
            //the page is loaded from memory????
            Page page=pages.get(mid);
            Integer minKey=(Integer)page.getMinKey();
            Integer maxKey=(Integer) page.getMaxKey();

           int n= Page.maxSize;
            if((primaryKeyValue>minKey && primaryKeyValue<maxKey)|| page.currentSize<n)// either i shoould be put here or there is room for me to be put
                return mid; //the id of the page is valid to insert to

            else if (primaryKeyValue>maxKey)
                low=mid+1;

            else
                high=mid-1;


        }
        //if i did not return anything from the loop then my ID is smaller than the minimum or greater than the maximum

        Page lastPage=pages.get(pages.size()-1);
        Page firstPage=pages.get(0);

        if(primaryKeyValue>(Integer)lastPage.getMaxKey())
            return pages.size()-1;  //must be inserted at end  , 0 indexed so we put -1
        else
            return 0;              //must be inserted at first page

    }

    private int searchByDouble(Double x) {
        //binary search code
        return  0;
    }

    private int searchByString(String x) {
        //binary search code
        return 0;

    }

    private int searchByDate(Date x) {
        //binary search code
        return 0;

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
