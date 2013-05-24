/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dict;

import java.io.IOException;

/**
 *
 * @author Николай
 * абстрагирует работу с приемником текста
 * abstract interaction to text receiver
 */
public interface IWriter {
    
    public void Write(String str, WriteEnum destination) throws IOException;
    
}
