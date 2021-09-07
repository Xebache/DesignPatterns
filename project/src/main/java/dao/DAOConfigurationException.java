package dao;


class DAOConfigurationException extends RuntimeException {

    public DAOConfigurationException(String message) {
        super( message );
    }

    public DAOConfigurationException(String message, Throwable cause) {
        super( message, cause );
    }

}
