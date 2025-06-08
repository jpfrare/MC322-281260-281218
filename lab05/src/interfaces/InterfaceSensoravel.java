package interfaces;

import exceptions.ColisaoException;
import exceptions.ForaDosLimitesException;
import exceptions.RoboDesligadoException;

public interface InterfaceSensoravel {
    public void acionarSensores() throws ForaDosLimitesException, ColisaoException, RoboDesligadoException;
}
