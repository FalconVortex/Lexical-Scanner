/*
 *  Copyright Angelo Bradley 2018
 */
package hw3;

import static hw3.Type.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Angelo Bradley
 * @author Alisea Wiggins
 * @author Teon Parker
 */
public class Tokenizer {

    public Tokenizer() {

    }

    public ArrayList<Token> setTokenList(String importedDoc) throws FileNotFoundException {

        File myFile = new File(importedDoc);
        //The scanner will tokenize the string, generating tokens based on white-space separation...
        Scanner myData = new Scanner(myFile);

        /*-----------------------------Tokenizer-------------------------------------*/
        ///...what follows is a collection of processes for generating individual tokens where
        //the scanner may have combined two or more tokens (ie "p;" is separated into "p" and ";")
        //this is also where tokens are given an associative type for comparison by the parser
        ArrayList<Token> tokens = new ArrayList<Token>();

        while (myData.hasNext()) {

            String capture = myData.next();

            if (capture.contains(";") || capture.contains(",")) {
                //ie. p;
                Token tok = new Token(capture.substring(0, 1), IDENT); // capture.substring(0,1) = p;
                tokens.add(tok);

                Token tok2 = new Token(capture.substring(1), (capture.substring(1).equals(";")) ? SEMICOLON : COMMA); //capture.substring(1) = ;
                tokens.add(tok2);

            } else if (capture.contains("(") || capture.contains(")")) {
                //ie. programName()
                //subCap --> substring of capture
                String subCap1 = capture.substring(0, capture.length() - 2); //capture function name --> programName
                String subCap2 = capture.substring(capture.length() - 2, capture.length() - 1); //capture open parenthesis --> (
                String subCap3 = capture.substring(capture.length() - 1); //capture closing parenthesis --> )

                Token tok = new Token(subCap1, FUNCNAME);
                tokens.add(tok);

                Token tok2 = new Token(subCap2, (subCap2.equals("(")) ? OPENPAREN : CLOSEPAREN);
                tokens.add(tok2);

                Token tok3 = new Token(subCap3, (subCap3.equals("(")) ? OPENPAREN : CLOSEPAREN);
                tokens.add(tok3);

            } else if (capture.contains("{") || capture.contains("}")) {

                Token tok = new Token(capture, (capture.equals("{")) ? OPENBRACK : CLOSEBRACK);
                tokens.add(tok);

            } else if (capture.length() == 1 && capture.matches("[a-z]")) {
                //enforces the EBNF established rule that a variable name would be a single letter 
                //and allows the program to differentiate variable names from keywords and program names
                Token tok = new Token(capture, IDENT);
                tokens.add(tok);

            } else if (capture.equals("+") || capture.equals("-") || capture.equals("=")) {

                Token tok = new Token(capture, (capture.equals("=")) ? ASSIGN : ARITH);
                tokens.add(tok);

            } else {

                Token tok = new Token(capture, KEYWORD);
                tokens.add(tok);
            }

        }
        myData.close();

        return tokens;
    }

}
