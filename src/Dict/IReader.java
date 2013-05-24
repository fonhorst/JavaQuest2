/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dict;

import java.io.IOException;

/**
 *
 * @author Николай
 * 
 * абстрагирует работу с источником текста
 */
public interface IReader {
    
    public String Read(ReadEnum command) throws IOException;
   
}
