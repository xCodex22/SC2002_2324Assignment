package staffmanagement;

/**
 * Filter options for displaying staff
 */
public enum FilterOption {
  /**
   * display all staff
   */ 
  ALL, 
  
  /**
   * filter by role, either doctor or pharmacist
   */
  ROLE, 

  /**
   * filter by gender, male, female or others
   */
  GENDER, 

  /**
   * filter by age
   */
  AGE, 

  /**
   * filter by searching the ID
   */
  ID,
}
