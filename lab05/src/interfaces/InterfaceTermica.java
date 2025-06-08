package interfaces;

import exceptions.ColisaoException;
import exceptions.ForaDosLimitesException;
import exceptions.RoboDesligadoException;

public interface InterfaceTermica {
    //comportamento varia se a classe prefere baixas ou altas temperaturas
    void preferenciaTermica() throws ForaDosLimitesException, ColisaoException, RoboDesligadoException;
}