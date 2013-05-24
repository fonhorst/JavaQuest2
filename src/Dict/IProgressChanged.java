/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dict;

/**
 *
 * @author Николай
 * интерфейс, используется когда вызывающая сторона хочет сообщить об изменение состояния прогресса
 */
public interface IProgressChanged {
    //функция для передачи данных о состоянии прогресса управляющему объекту
    public void ProgressChanged(ProgressChangedEventArgs e);
    
}
