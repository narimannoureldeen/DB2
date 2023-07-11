import java.util.*;

public class Page {


    public static Integer maxSize;   //max num of records page can have static because all pages share this attribute
    Integer currentSize;            //current number of records present in page
    Object maxKey;                 //max value of key currently in this page     for binary search
    Object minKey;                //minimum value of currently key in this page for binary search
    Vector<Tuple> allRecords;    //vector of the actual records (just like an array)
    Integer pageId;             //page has an id in order to load it from the disk
    Integer maxIndex;          //maximum index that contains a record probably will be used in shifting





    public static void setMaxSize(Integer n)
   {
       maxSize=n;
   }

   public Page(int pageId)
   {
       this.pageId=pageId;
       currentSize=0;
       allRecords=new Vector<>();
   }


   public void  insert(Hashtable<String,Object> record,Object primaryKey)
   {
//       // Storing all entries of Hashtable
//       // in a Set using entrySet() method
//       Set<Map.Entry<String, Object>> entrySet = record.entrySet();
//
//       // Iterating through the Hashtable object
//       // using for-each loop
//       for (Map.Entry<String, Object> entry : entrySet)
//       {
//           String key=entry.getKey();
//           Object value=entry.getValue();
//           int x=getTypeof(value);
//
//           //insert normally and update min and max of page accordingly and update maxIndex also
//           // we only call this method when we are sure that there is space in this page or we emptied space for it
//
//
//       }



   }


   public Tuple insert (Tuple tuple)
   {

       if(currentSize<maxSize)
       {
           String primaryKeyName=tuple.primaryKey;
           Object primaryKeyValue = tuple.record.get(primaryKeyName);  //i got the value of the primary key to know where to insert the tuple in the page
           int indexOfTuple=getTupleIndex(primaryKeyValue);
           if(indexOfTuple==currentSize)
           {
               allRecords.add(currentSize,tuple);
               maxKey=Math.max(maxKey,);
               minKey=Math.min(minKey,);
               currentSize++;
           }
           else
           {
               shift(indexOfTuple);   // i will shift all the records starting from this index

               allRecords.add(indexOfTuple,tuple);
               maxKey=Math.max(maxKey,);
               minKey=Math.min(minKey,);
               currentSize++;
           }
           return null;
       }
       else  //page is full!
       {
           String primaryKeyName=tuple.primaryKey;
           Object primaryKeyValue = tuple.record.get(primaryKeyName);  //i got the value of the primary key to know where to insert the tuple in the page
           int indexOfTuple=getTupleIndex(primaryKeyValue);

           Tuple removedTuple=shift(indexOfTuple);

           allRecords.add(indexOfTuple,tuple);
           maxKey=Math.max(maxKey,);
           minKey=Math.min(minKey,);
           return removedTuple;
       }


   }




   public Tuple shift(int index)
   {
       int i=currentSize-1;
       Tuple lastTuple = allRecords.get(i);
       for(;i>index-1;i--)
       {
           Tuple temp=allRecords.remove(i);
           allRecords.add(i,temp);
       }
       return lastTuple;

       //now the index is empty so that a value can be inserted into it
   }









    public int getTupleIndex(Object primaryKeyValue)
    {

        int type=getTypeof(primaryKeyValue);  //returns an integer that indiactes the type of object

        //for each type we have a specific method
        switch (type)
        {
            case 1:return searchByIntger((Integer)primaryKeyValue);


           // case 2:return searchByString((String) primaryKeyValue);


            //case 3:return searchByDouble((Double) primaryKeyValue);


           // case 4:return searchByDate((Date) primaryKeyValue);



            default:return  -1;

        }


    }



    //all the search methods just search on by the specific clusteringColumn type
    private int searchByIntger(Integer primaryKeyValue) {
        //binary search code
        int high=currentSize-1;
        int low=0;

        while(low<=high)
        {
            int mid=(low+high)/2;

            Tuple tuple1 = allRecords.get(mid);
            Integer value1=tuple1.getTuplePrimaryKeyValueInteger();
            if(mid==0)
                return 0;
            else if(mid==currentSize-1)
                return currentSize=1;

            Tuple tuple2 = allRecords.get(mid-1);
            Integer value2=tuple2.getTuplePrimaryKeyValueInteger();

            if(primaryKeyValue<value1 && primaryKeyValue>value2)
                return mid;

            else if (primaryKeyValue<value1)
                high=mid-1;
            else
                low=mid+1;


        }

        return -1; //not supposed to reach this line


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




    public Integer getMaxSize() {
        return maxSize;
    }

    public Object getMaxKey() {
        return maxKey;
    }

    public Object getMinKey() {
        return minKey;
    }

    public Vector<Tuple> getAllRecords() {
        return allRecords;
    }

    public Integer getPageId() {
        return pageId;
    }

    public Integer getCurrentSize() {
        return currentSize;
    }



}
