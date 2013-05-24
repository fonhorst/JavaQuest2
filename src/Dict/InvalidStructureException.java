/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dict;

/**
 *
 * @author Николай
 * исключение бросаемое при неверной структуре словаре 
 * exception for invalid structure of Dictionary
 */
public class InvalidStructureException extends Exception {
    public InvalidStructureException(String message)
    {
        super(message);
    }
}
