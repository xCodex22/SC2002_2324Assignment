package account;

/**
 * represents the login status of login attempts
 */
public enum LoginStatus {
  /**
     * attempt is successful
     */
  SUCCESS, 

  /**
   * user is not found
   */
  USER_NOT_FOUND, 

  /**
   * wrong password 
   */
  WRONG_PASSWORD, 

  /**
   * unexpected error
   */
  ERROR
}
