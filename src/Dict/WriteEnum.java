/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dict;

/**
 *
 * @author Николай
 * перечисление, определяющее режимы записи
 * contain write modes
 */
public enum WriteEnum {
    
    //продолжить запись в приемник 
    ContinueWriting,
    //записать данные и закрыть приемник
    //переоткрытие недоступно
    //reopen isn't allowed
    StopWriting,
    //закрыть текущий приемник и открыть новый
    //close current receiver and open another
    ContinueWritingWithNewFile
}

