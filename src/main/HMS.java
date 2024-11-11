package main;

import account.AccountSystem;
import menu.*;
import users.User;
import users.Patient;

import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * The main class of hospital management system (hms).
 * This is the entry point for the CLI
 */
public class HMS {
  /**
   * initialises the menu  user interface to start the cli
   * @param default CLI args, doesn't do anything
   */
  public static void main(String[] args) {
    Menu menu = new Menu();
    menu.start();    
  }
}

