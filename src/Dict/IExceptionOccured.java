/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dict;

/**
 *
 * @author Николай
 * интерфейс, используется когда вызывающая сторона хочет сообщить о возникновении исключения
 */
public interface IExceptionOccured {
    
    //функция для передачи исключения управляющему объекту
    public void ExceptionOccured(Exception e);
    
}
