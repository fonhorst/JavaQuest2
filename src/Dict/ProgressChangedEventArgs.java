/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dict;

/**
 *
 * @author Николай
 * класс для описания изменения прогресса, 
 * используется в событиях изменения состояния прогресса
 */
public class ProgressChangedEventArgs {
    
    public ProgressChangedEventArgs(String act,String inf,int n,int all,boolean fin)
    {
        this.action=act;
        this.info=inf;
        this.count=n;
        this.allCount=all;
        this.finish=fin;
    }
    
    private String action;
    private String info;
    private int count;
    private int allCount;
    private boolean finish=false;
    private boolean cancel=false;
    
    //название действия
    //name of action
    public String getAction(){return this.action;}
    //информация о действии
    //gets info about action
    public String getInfo(){return this.info;}
    //количественная характеристика действия на текущий момент
    //number of done piece of task(usually it's line count)
    public int getCount(){return this.count;}
    //максимальное количество действия
    // number of all piece of task
    public int getAllCount(){return this.allCount;}
    //флаг, показывает что действие завершено
    // indicator for finished action
    public boolean getFinish(){return this.finish;}
    //флаг, принимается для передачи информации о прекращении действия
    //indicator for interrupting of action
    public void setCancel(boolean Cancel){this.cancel=Cancel;}
    //флаг, отображает прекращение действия
    // indicator for interrupted action
    public boolean getCancel(){return this.cancel;}
    
    
}
