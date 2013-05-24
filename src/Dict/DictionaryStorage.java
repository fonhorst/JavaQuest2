/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//Model

package Dict;

import java.io.IOException;
import java.util.HashSet;

/**
 *
 * @author Николай
 * класс хранилище словаря и организации к нему доступа
 */
public class DictionaryStorage {
    //словарь
    private HashSet<String> dictionary=new  HashSet<String>();
    //индикатор загруженности словаря
    private boolean isLoad=false;
    //делегат для события изменения прогресса
    private HashSet<IProgressChanged> events = new HashSet<IProgressChanged>();
    //делегат для события изменения прогресса
   public void addProgressChangedHandler(IProgressChanged receiver)
   {
       this.events.add(receiver);
   }
   public boolean removeProgressChangedHandler(IProgressChanged receiver)
   {
	   return this.events.remove(receiver);
   }
   
    //проверка валидности строк, добавляемых в словарь 
    private boolean IsValid(String line)
    {
        for(char i:line.toCharArray())
            if (!(Character.isLetterOrDigit(i) || (i=='_') || (i=='-')))  return false;
        return true;
    }
  
    private void OnProgressChanged(ProgressChangedEventArgs e)
    {
    	for(IProgressChanged elt:this.events)
    		elt.ProgressChanged(e);
    }
    //receiver - объект приема информации об изменении состояния прогресса
    public DictionaryStorage() 
    {
       //this.events=receiver;
    }
    
    //индикатор загруженности
    public boolean IsLoad()
    {
        return isLoad;
    }
    //добавляет элемент в словарь
    //в строке должно быть одно слово, состоящее из букв, цифр или из _,-, но не должно с них начинаться
    public boolean Add(String el)
    {
        if (this.IsValid(el))
        {
            this.dictionary.add(el.toLowerCase());
            return true;
        }
        else return false;
    }
    //проверяет содержится ли данное слово в словаре
    //в строке должно быть одно слово, состоящее из букв, цифр или из _,-, но не должно с них начинаться
    public boolean Contains(String el)
    {
         return this.dictionary.contains(el.toLowerCase());
    }
    //функция для загрузки словаря из источника данных,
    //reader - источник данных
    //lineCount количество строк в источнике
    public void Load(IReader reader,int lineCount) throws IOException, InvalidStructureException
    {
         String line;
         
         this.dictionary.clear();
         int i=0;
         
        try
        {
            ProgressChangedEventArgs e=null;
            while((line=reader.Read(ReadEnum.ContinueReading))!=null)
            {
                line=line.trim();
                if (!this.Add(line))
                    throw new InvalidStructureException("Словарь имеет неправильную структуру");
                ++i;
                
               this.OnProgressChanged(e = new ProgressChangedEventArgs("Загрузка словаря","Загружено строк: "+i,i,lineCount,false));
	           if (e.getCancel())
	           {
	               reader.Read(ReadEnum.StopReading);
	               isLoad=false;
	               return;
	           }
            }
        }
        catch(IOException e)
        {
            this.dictionary.clear();
            isLoad=false;
            throw e;
        }
        catch(InvalidStructureException e)
        {
            this.dictionary.clear();
            isLoad=false;
            reader.Read(ReadEnum.StopReading);
            throw e;
        }
        isLoad=true;
    }
}
