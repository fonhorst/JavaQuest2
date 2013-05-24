/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//Controller
package Dict;
/*
 * 0) Block static and non-static initialization
 * 
 * 1) Private static fields
 * 2) Protected static fields
 * 3) Public static fields 
 * 
 * 4) Private fields
 * 5) Protected fields
 * 6) Public fields
 * 
 * 7) Private ctors
 * 8) Protected ctors
 * 9) Public ctors
 * 
 * 10) Private static methods
 * 11) Protected static methods
 * 12) Public static methods
 * 
 * 13) Private methods
 * 14) Protected methods
 * 15) Public methods
 *  */



import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;
/**
 *
 * @author Николай
 * управляющий функционалом класс
 * реализуется паттерн "Фасад"
 */
public class ProcessingManager 
    {
	///////////////////////////////Private Fields//////////////////////////////////////////////
    //адрес словаря
    private String dictionaryAddress="";
    //адрес файла с текстом
    private String textAddress="";
    //максимальное количество строк на файл
    private int max;
    //объект управления вводом - выводом
    private InOutManager inout=new InOutManager();
    //объект управления словарем
    private DictionaryStorage dStorage;
    //переменная хранящая префикс для обрабатываемых слов
    private String prefix="<b><i>";
    //переменная хранящая постфикс для обрабатываемых слов
    private String postfix="</b></i>";
    //делагат для обработки события возникновения исключения
    private HashSet<IExceptionOccured> exOccur=new HashSet<IExceptionOccured>();
    //делегат для обработки события возникновения измения прогресса
    private HashSet<IProgressChanged> pChange=new HashSet<IProgressChanged>();
    
    ///////////////////////////////Public ctors//////////////////////////////////////////////
    //конструктор по-умолчанию
    public ProcessingManager() {
    }
    
    ///////////////////////////////Public static methods//////////////////////////////////////////////
    /* устанавливает при необходимости значение максимального количества строк в правильный диапазон
     * @param int макс кол-во строк на файл
     * */
    public static int MaxLines(int n)
    {
        if (n<10) n=10;
        else if (n>100000) n=100000;
        return n;
    }
    
    ///////////////////////////////Protected methods//////////////////////////////////////////////
    /*
     * 	обработка слов в строке, если они есть в словаре 
     	@return -1 - окончания предложения в строке нет
     	@return -2 - окончание предложения заканчивает строку
     	@return положительное число - координата окончания предложения в строке
     	ищем последнее окончание предложения (т.к. строки в выходном файле непревышают длины строк во входном файле)
     	 @param line строка на обработку 
     * */     
    protected int LineProcessing(AtomicReference<String> line)
    {
        String buf="";
        int numberEnd = -1;
      //общее приращение
        int augment=0;
         
        int increment=0;
         char[] lineMas=line.get().toCharArray();
               
                for(int i=0;i<lineMas.length;++i)
                {
                    //слово обязательно должно начинаться с буквы или цифры
                    if (Character.isLetterOrDigit(lineMas[i]))
                    {
                        String word="";
                        //собираем слово
                        word+=lineMas[i];
                        ++i;
                    
                        		
                        while((i<lineMas.length) && ((Character.isLetterOrDigit(lineMas[i]) || (lineMas[i]=='_') || (lineMas[i]=='-'))))
                        {
                            word+=lineMas[i];
                            ++i;
                        }
                         --i;
                        ///
                         
                         //добавление в результирующую строку
                        if (this.dStorage.Contains(word))
                        {
                            buf+=this.prefix+word+this.postfix;
                            increment+=this.prefix.length()+this.postfix.length();
                        }
                        else buf+=word;
                        ///
                       
                    }
                    //обработка окончания предложения
                    else if ((lineMas[i]=='.') || (lineMas[i]=='!') || (lineMas[i]=='?'))//или . с пробелом?
                    {
                        int number=i;
                        buf+=lineMas[i];
                        //после окончания предложения обязательно должна идти точка
                        //данный случай не конец предложения
                        if ((i+1<lineMas.length) && (lineMas[i+1]!=' '))
                            continue;
                        
                      
                        ++i;
                        //конец предложения
                       while((i<lineMas.length) && (lineMas[i]==' '))
                       {
                           buf+=lineMas[i];
                           ++i;
                       }
                       
                       //это действительно окончание предложения
                       if (((i<lineMas.length) && (((Character.isLetter(lineMas[i])) && (lineMas[i]==Character.toUpperCase(lineMas[i]))) || (Character.isDigit(lineMas[i]))) )) 
                       {
                           numberEnd=number;
                           augment+=increment;
                           increment=0;
                       }
                       else if (i==lineMas.length)
                       {
                           numberEnd=-2;//окончание предложение - окончание строки
                           break;
                       }
                       --i;
                    }
                    //остальные знаки игнорируются
                    else buf+=lineMas[i];
                } 
                
        line.set(buf);
        return (numberEnd>0)?numberEnd+augment:numberEnd;
    }
    /*
     * обработка данных и формирование выходных файла(ов)
     * @param dictAddress - адрес словаря
     * @param textAddr - адрес файла с текстом
     * @param outAddr - адрес выходного файла
     * @param maxLines - максимальное количество строк в файле
     * */
    protected void Run(String dictAddress,String textAddr,String outAddr,int maxLines)
    {
        this.max=maxLines;
        
        try
        {
            this.LoadDictionary(dictAddress);
            this.textAddress=textAddr;
        }
        catch (InvalidStructureException ex)
        {
            
         this.OnExceptionOccured(new Exception(" У словаря неверная структура"));
         return;
            
        }
        catch (FileNotFoundException ex) 
        {
            this.OnExceptionOccured(new Exception(" Файл словаря не существует"));
            return;
        }
        catch (IOException ex) 
        {
            this.OnExceptionOccured(new Exception(" Ошибка при чтении словаря"));
            return;
        }
        catch (SecurityException ex) 
        {
            this.OnExceptionOccured(new Exception(" Ошибка прав доступа при чтении словаря"));
            return;
        }
        catch(IllegalArgumentException ex)
        {
            this.OnExceptionOccured(new Exception(" Неправильно задан адрес словаря"));
            return;
        }
        
        try
        {
            int count=InOutManager.GetLinesCount(textAddr);
            IReader reader = InOutManager.SetInput(textAddr);
            String textBegin="<html>"+this.inout.getSeparator()+"<head>"+this.inout.getSeparator()+
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1251\">"+this.inout.getSeparator()+
            "</head>"+this.inout.getSeparator()+"<body>";
            String textEnd="</body>"+this.inout.getSeparator()+"</html>";
            IWriter writer = InOutManager.SetOutput(outAddr, textBegin, textEnd);
            this.ProcTask(reader, writer,count);
        }
        catch (FileNotFoundException ex) 
        {
            this.OnExceptionOccured(new Exception(" Файл c текстом не существует"));
            return;
        } 
        catch (IOException ex) 
        {
            this.OnExceptionOccured(new Exception(" Ошибка ввод/вывода либо при чтении файла с текстом, либо при записи  "));
            return;
        }
        catch (SecurityException ex) 
        {
            this.OnExceptionOccured(new Exception(" Ошибка прав доступа либо при чтении файла с текстом, либо при записи "));
            return;
        }
        catch(IllegalArgumentException ex)
        {
            this.OnExceptionOccured(new Exception(" Неправильно задан адрес файла с текстом"));
            return;
        }
    }
   
    /*
     * обрабатывает данные и создает выходные 
     * @param reader источник данных
     * @param writer приемник данных
     * @param lineCount количество строк для обработки из источника
     * */
    protected void ProcTask(IReader reader,IWriter writer,int lineCount) throws IOException
    {
        
        this.OnProgressChanged(new ProgressChangedEventArgs("Обработка текста","Обработано строк: "+0,0,lineCount,false));
            String line;
            int n=0;
            int count=0;
            int k=0;
            String buf="";
             boolean flag=false;
             int t=1;
             AtomicReference<String> ref;
             ProgressChangedEventArgs e;
            while ((line = reader.Read(ReadEnum.ContinueReading)) != null)
            {
               int result = this.LineProcessing(ref=new AtomicReference<String>(line));
               if (result==-1) buf+=ref.get();
               else
               {
                   String tail="";
                   if (result==-2) buf+=ref.get();
                   else 
                   {
                       buf+=ref.get().substring(0,result+1);
                       tail=ref.get().substring(result+2,ref.get().length());
                   }
               
                    if (n<this.max)
                     {
                     
                        writer.Write(buf, WriteEnum.ContinueWriting);
                        buf=tail;
                        if (result==-2) {k=n+1;flag=true;}
                        else {k=n;flag=false;}             
                    }
                    else
                    {
                        if (flag)
                        {
                            buf=buf.substring(4+this.inout.getSeparator().length(),buf.length());
                            flag=false;
                        }
                        //дописываем хвост, закрываем старый файл,открываем новый файл, пишем заголовки, дописываем остатки от предыдущего
                        writer.Write(buf, WriteEnum.ContinueWritingWithNewFile);
                        buf=tail;
                        if (n-k>this.max)
                        {
                            writer.Write("", WriteEnum.ContinueWritingWithNewFile);
                            n=0;
                        }
                        else n=n-k;//-1;
                    }
               }
               buf+="<br>"+this.inout.getSeparator();
                ++n;
                ++count;
                this.OnProgressChanged(e=new ProgressChangedEventArgs("Обработка текста","Обработано строк: "+count,count,lineCount,false));
                if (e.getCancel())
                {
                   buf="<br>"+this.inout.getSeparator();
                   reader.Read(ReadEnum.StopReading);
                   break;
                }
               /* try {
                    Thread.currentThread().sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProcessingManager.class.getName()).log(Level.SEVERE, null, ex);
                    }*/
            }
            String h=buf.substring(0,buf.length()-("<br>"+this.inout.getSeparator()).length());
            //например забыли точку в конце текста
            if (!h.equals(""))
            {
                if (n<this.max)
                    writer.Write(buf, WriteEnum.ContinueWriting);
                else
                {
                    //дописываем хвост, закрываем старый файл,открываем новый файл, пишем заголовки, дописываем остатки от предыдущего
                    writer.Write("", WriteEnum.ContinueWritingWithNewFile);
                    
                }
            }
            writer.Write(buf, WriteEnum.StopWriting);
             this.OnProgressChanged(new ProgressChangedEventArgs("Обработка текста","Обработано строк: "+n,n,lineCount,true));
        
    }
    
    ///////////////////////////////Public methods//////////////////////////////////////////////
    /*
     * загрузка словаря из файла 
     * @param dictAddress адрес словаря
     * */
    public void LoadDictionary(String dictAddress) throws FileNotFoundException, IOException, InvalidStructureException,IllegalArgumentException
    {
       
        //если адрес тот же, то грузить словарь не будем
        if ((this.dictionaryAddress.endsWith(dictAddress)) && (this.dStorage!=null) && (this.dStorage.IsLoad())) return;
        if (this.dStorage==null) 
        {
        	this.dStorage = new DictionaryStorage();
        	for(IProgressChanged elt:this.pChange)
        		this.dStorage.addProgressChangedHandler(elt);
        }
        
       this.dictionaryAddress=dictAddress;
       int count=InOutManager.GetLinesCount(dictAddress);
       this.dStorage.Load(InOutManager.SetInput(dictAddress),count);
       
    }
  
    /*
     * публичный доступ для выполнения операции обработки текстового файла 
     * @param dictAddress адрес словаря
     * @param textAddr адрес файла с текстом
     * @param outAddr адрес выходного файла
     * @param maxLines максимальное количество строк на файл
     * */
    public void Process(String dictAddress,String textAddr,String outAddr,int maxLines)
    {
        new Thread(new WorkerThread(this,dictAddress,textAddr,outAddr,maxLines)).start();
    }
    //строка вставляемая перед обрабатываемым словом(в случае если оно есть в словаре)
    
    public String getPrefix()
    {
        return this.prefix;
    }
    //строка вставляемая после обрабатываемого слова(в случае если оно есть в словаре)
    public String getPostfix()
    {
        return this.postfix;
    }
  //строка вставляемая перед обрабатываемым словом(в случае если оно есть в словаре)
    public void setPrefix(String pref)
    {
        if (pref!=null)
            this.prefix=pref;
        else this.prefix="";
    }
    //строка вставляемая после обрабатываемого слова(в случае если оно есть в словаре)
    public void setPostfix(String post)
    {
        if (post!=null)
            this.postfix=post;
        else this.postfix="";
    }
     //добавление делагата для обработки события возникновения исключения
    public void addExceptionOccuredHandler(IExceptionOccured occur)
    {
        this.exOccur.add(occur);       
    }
    public boolean removeExceptionOccuredHandler(IExceptionOccured occur)
    {
    	return this.exOccur.remove(occur);
    }
    //добавление делегата для обработки события возникновения измения прогресса
    public void addProgressChangedHandler(IProgressChanged change)
    {
        this.pChange.add(change);
        if(this.dStorage!=null)
            this.dStorage.addProgressChangedHandler(change);
    }
    public void removeProgressChangedHandler(IProgressChanged change)
    {
    	this.pChange.remove(change);
    	if(this.dStorage!=null)
            this.dStorage.removeProgressChangedHandler(change);
    }
    private void OnProgressChanged(ProgressChangedEventArgs e)
    {
    	for(IProgressChanged elt:this.pChange)
    		elt.ProgressChanged(e);
    }
    private void OnExceptionOccured(Exception ex)
    {
        for(IExceptionOccured elt:this.exOccur)
            elt.ExceptionOccured(ex);
    }
    
    ///////////////////////////////Internal classes//////////////////////////////////////////////
    //класс выражающий методы для запуска потока
    class WorkerThread implements Runnable
    {
        private String dictionaryAddress;
        private String txtAddress;
        private String outAddress;
        private int max;
        private ProcessingManager pManager;
        
        //конструктор, принимает параметры которые надо передать в функцию
        public WorkerThread(ProcessingManager manager,String dictAddress,String textAddr,String outAddr,int maxLines)
        {
            this.pManager=manager;
            this.dictionaryAddress=dictAddress;
            this.txtAddress=textAddr;
            this.outAddress=outAddr;
            this.max=maxLines;
            
        }
        
        public void run() {
                this.pManager.Run(this.dictionaryAddress, this.txtAddress,this.outAddress, max);
        }
    }

   
}
