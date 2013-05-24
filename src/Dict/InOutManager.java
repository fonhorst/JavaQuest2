/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dict;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Николай
 * класс - система работы с источником ввода и вывода
 */
public class InOutManager implements IReader,IWriter {
   //чтение файла
    protected BufferedReader input;
    //запись файла
    protected BufferedWriter output;
    //макс количество строк в файле
    protected int MaxLinesNumber=100000;
    private int currentLineNumber=0;
    
    //предопределенный заголовок для приемника
    private String caption;
    //предопределенный заголовок для приемника
    private String ending;
    
    private String outAddress;
    //идентификатор, которым дополняется имя при создании нового приемника
    private int number=1;
    private String lineSeparator=System.getProperty("line.separator");
    
    public InOutManager()
    {
        
    }
    private InOutManager(BufferedReader reader)
    {
        this.input=reader;
    }
    private InOutManager(BufferedWriter writer,String fileAddress,String capt,String end) throws UnsupportedEncodingException, FileNotFoundException
    {
            this.number=0;
            this.caption=capt;
            this.ending=end;
            this.outAddress=fileAddress;
            output=writer;
    }
    //получить стандартный разделитель строки
    public String getSeparator()
    {
        return this.lineSeparator;
    }
    
    //чтение строки из источника
    public String Read(ReadEnum command) throws IOException
    {
        try
        {
            switch(command)
            {
                case ContinueReading:
                {
                    String line=this.input.readLine();
                    if (line==null)
                        input.close();
                    return line;
                }
                case StopReading:
                {
                    if (input!=null)
                        input.close();
                    return null;
                }
                default:throw new UnsupportedOperationException("Комманда "+command+" не поддерживается");
            }
                    
        }
        catch(IOException e)
        {
            try
            {
                if (input!=null) input.close();
            }
            finally
            {
                input=null;
                throw e;
            }
            
        }
    }
    
    //создает источник для чтения 
    //по заданному адресу файла fileAddress
    //(делегируем действия этого класса)
    public static IReader SetInput(String fileAddress) throws FileNotFoundException, IOException,SecurityException,IllegalArgumentException
    {
        BufferedReader br=null;
        try
        { 
           return new InOutManager(br=new BufferedReader(new InputStreamReader(new FileInputStream (fileAddress), "Cp1251" )));
            
        }
        catch(IllegalArgumentException e)
        {
            //input=null;
            throw e;
        }
        catch(FileNotFoundException e)
        {
            //input=null;
            throw e;
        }
        catch(IOException e)
        {
            try
            {
                if (br!=null) br.close();
            }
            finally
            {
                br=null;
                throw e;
            }
             
        }
      
    }
    
    //создает приемник для записи
    //по заданному адресу файла fileAddress,
    //и задает предустановленные заголовок и концовку 
    // для файлов, которые создаются при записи
        //(делегируем действия этого класса)
    public static IWriter SetOutput(String fileAddress,String capt,String end) throws IOException,SecurityException,IllegalArgumentException
    {
        BufferedWriter bw=null;
        try
        {
            
            int k=fileAddress.lastIndexOf(".");
            bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream (fileAddress.substring(0,k+1)+"html"), "Cp1251" ));
            bw.write(capt);
            return new InOutManager(bw,fileAddress,capt,end);
            
        }
        catch(IllegalArgumentException e)
        {
            //output=null;
            throw e;
        }
        catch(IOException e)
        {
            try
            {
                if (bw!=null) bw.close();
            }
            finally
            {
             bw=null;
             throw e;
            }
             
        }
    }
    
    //осуществляет запись в приемник
    public void Write(String data,WriteEnum destination) throws IOException,SecurityException
    {
        try
        {
 
            switch(destination)
            {
                case StopWriting:
                    this.output.write(data+this.lineSeparator+this.ending);
                    this.output.close();
                    break;
                case ContinueWritingWithNewFile:
                    this.output.write(this.lineSeparator+this.ending);
                    this.output.close();
                    int k=this.outAddress.lastIndexOf(".");
                    output=new BufferedWriter(new OutputStreamWriter(new FileOutputStream (this.outAddress.substring(0,k)+"_"+this.number+".html"), "Cp1251" ));
                    this.output.write(this.caption);
                    this.output.write(data);
                    ++number;
                    break;
                default:
                    this.output.write(data); 
                    break;
            }
            
           
        }
        catch(IOException e)
        {
            try
            {
                if (output!=null) output.close();
            }
            finally
            {
                output=null;
            }
            throw e;
        }
    }
   
    //считает количество строк в заданном файле fileAddress
    public static int GetLinesCount(String fileAddress) throws FileNotFoundException, UnsupportedEncodingException, IOException
    {
       BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream (fileAddress), "Cp1251" ));
       int i=0;
       while(br.readLine()!=null) ++i;
       br.close();
       return i;
    }
            
}
