import java.util.Date;
import java.util.Hashtable;

public class Tuple {
    Hashtable<String,Object> record ;
    String primaryKey;
    String tableName;




    public Tuple(Hashtable<String,Object> record,String tableName,String primaryKey)
    {
        this.record=record;
        this.tableName=tableName;
        this.primaryKey=primaryKey;
    }



    public void insertRecord()
    {
        //insert into the record hashtable here maybe will be changed into vector
        //check the input has all columns , no values are missing



    }

    public Integer getTuplePrimaryKeyValueInteger()
    {
        return (Integer) record.get(primaryKey);
    }
    public String getTuplePrimaryKeyValueString()
    {
        return (String) record.get(primaryKey);
    }
    public Date getTuplePrimaryKeyValueDate()
    {
        return (Date) record.get(primaryKey);
    }
    public Double getTuplePrimaryKeyValueDouble()
    {
        return (Double) record.get(primaryKey);
    }




}
