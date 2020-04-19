// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 Assignment 1
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.awt.Color;

/**
 * Checks dates, prints out in long format and draws calendars.
 * The processDates method
 * Reads a date from the user as three integers, and then
 * (a) checks that the date is valid (ie, represents a real date,
 *     taking into account leap years), reporting if it is not valid.
 *     (It may assume the standard modern calendar, and isn't required
 *      to give correct answers for dates before the 1600's when the
 *      calendar was different.
 * (b) If the date is valid, it prints out the date in a long form: eg
 *     Monday 3rd March, 2014. This requires working out which day
 *     of the week the date is.
 *     (Core only needs dates this year)
 * (c) It draws a one week calendar, highlighting the date:
 *     It shows the seven days of the week as a row of rectangles, highlighting
 *      the day corresponding to the date. It doesn't need to show the dates
 *      for each day.
 * (d) (Completion) It draws a monthly calendar for the month containing the date:
 *     It should draw a title containing the month and the year, and a
 *     grid of rectangles for each day of the month, giving the day of the month
 *     in each rectangle. This will be between 4 rows and 6 rows of 7 rectangles.
 *     The ISO standard for calendars specifies that the first day of each week
 *     should be a Monday.
 *     Ideally, the calendar should include the last few days of the previous
 *     month when the month doesn't start on a Monday and the first few days
 *     of the next month when the month doesn't end on a Sunday.
 *
 * Reasonable design would have a number of methods, for example:
 *  isValidDate  which would return a boolean (true or false)
 *  isLeapYear   which would return a boolean (true or false)
 *  findDay      which would return the day of the week as an int (0 to 6)
 *  drawWeek     which would draw the weekly "calendar"
 *  drawMonth    which would draw the monthly calendar
 * You might choose to design it differently, but doing it all in one huge method
 *  would not be good design.
 */

public class DateChecker {

    // constants:
    /*# YOUR CODE HERE */
    public static final double x = 50, y = 50;
    public static final double  width= 80, height = 60;

    public int day = 0;
    public String print = "";
    public String cycle = "";

    /**
     * Loop to repeatedly process a date.
     * Asks the user if they want to enter another date each time.
     */
    public void processDates(){
        do  {
            UI.clearText();
            UI.clearGraphics();
            processADate();
        }
        while (UI.askBoolean("Enter another date?")); 
    }

    /**
     * Asks user for a date, then produces the required output
     */
    public void processADate(){
        /*# YOUR CODE HERE */
        int date = UI.askInt("Day");
        int month = UI.askInt("Month"); //ONLY 2017 FOR CORE
        int year = UI.askInt("Year");
        int k = year % 100;
        int j = year / 100;

        //http://www.dreamincode.net/forums/topic/248224-calculate-day-of-the-week/
        if(dateValid(date,month,year)==true){
           
            if(month ==1){
                day = ((date + (((month+1) * 26) / 10) + k + (k / 4) + (j / 4))+ (5 * j)) % 7;
                cycle = "January";
            }
            else if(month ==2){
                day = ((date + (((month +1 ) * 26) / 10) + k + (k / 4) + (j / 4))+ (5 * j)) % 7;
                cycle = "February";
            }
            else if(month ==3){
                day = ((date + (((month+1 ) * 26) / 10) + k + (k / 4) + (j / 4))+ (5 * j)) % 7;
                cycle = "March";
            }
            else if(month ==4){
                day = ((date + (((month +1) * 26) / 10) + k + (k / 4) + (j / 4)) + (5 * j)) % 7;
                cycle = "April";
            }
            else if(month ==5){
                day = ((date + (((month+1  ) * 26) / 10) + k + (k / 4) + (j / 4))+ (5 * j)) % 7;
                cycle = "May";
            }
            else if(month ==6){
                day = ((date + (((month+1) * 26) / 10) + k + (k / 4) + (j / 4))+ (5 * j)) % 7;
                cycle = "June";
            }
            else if(month ==7){
                day = ((date + (((month+1) * 26) / 10) + k + (k / 4) + (j / 4))+ (5 * j)) % 7;
                cycle = "July";
            }
            else if(month ==8){
                day = ((date + (((month+1) * 26) / 10) + k + (k / 4) + (j / 4))+ (5 * j)) % 7;
                cycle = "August";
            }
            else if(month ==9){
                day = ((date + (((month+1) * 26) / 10) + k + (k / 4) + (j / 4))+ (5 * j)) % 7;
                cycle = "September";
            }
            else if(month ==10){
                day = ((date + (((month+1) * 26) / 10) + k + (k / 4) + (j / 4)) + (5 * j)) % 7;
                cycle = "October";
            }
            else if(month ==11){
                day = ((date + (((month+1) * 26) / 10) + k + (k / 4) + (j / 4)) + (5 * j)) % 7;
                cycle = "November";
            }
            else if(month ==12){
                day = ((date + (((month+1) * 26) / 10) + k + (k / 4) + (j / 4))+ (5 * j)) % 7;
                cycle = "December";
            }

            for (int i = 0; i <7; i++) {
                UI.drawRect(x + (width * i), y +(height), width, height);
                if(i==0){
                    UI.drawString("Monday", x+(width*i)+5, y + height + height/2);
                }
                else if(i==1){
                    UI.drawString("Tuesday", x+(width*i)+5, y + height +height/2);
                }
                else if(i==2){
                    UI.drawString("Wednesday", x+(width*i)+5, y + height +height/2);
                }
                else if(i==3){
                    UI.drawString("Thursday", x+(width*i) +5, y + height +height/2);
                }
                else if(i==4){
                    UI.drawString("Friday", x+(width*i) +5, y + height +height/2);
                }
                else if(i==5){
                    UI.drawString("Saturday", x+(width*i)+5, y + height +height/2);
                }
                if(i==6){
                    UI.drawString("Sunday", x+(width*i)+5, y + height +height/2);
                }

            }

        }
        UI.println(printDate(day,date,cycle,year));
    }

    public String printDate(int day, int date, String cycle, int year){
        if(day ==1){
            print = "Monday," + date + " " + cycle +"," + year;
        }
        if(day ==2){
            print = "Tuesday," + date + " " + cycle +"," + year;
        }
        if(day ==3){
            print = "Wednesday," + date + " " + cycle +"," + year;
        }
        if(day ==4){
            print = "Thursday," + date + " " + cycle +"," + year;
        }
        if(day ==5){
            print = "Friday," + date + " " + cycle +"," + year;
        }
        if(day ==6){
            print = "Saturday," + date + " " + cycle +"," + year;
        }
        if(day ==7){
            print = "Sunday," + date + " " + cycle +"," + year;
        }
        return print;
    }

    public boolean dateValid(int date, int month, int year){
        if((month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12) && date>0 && date<=31){
            return true;
        }else if ((month==4 || month==6 || month==9 || month==11) && date>0 && date<=30){
            return true;
        } else if(month==2 && leapYear(year)==true && date>0 && date<=29){
            return true;
        }else if(month==2 && leapYear(year)==false && date>0 && date<=28){
            return true;
        }
        return false;
    }

    public boolean leapYear(int year){
        if((year%4==0 && year%100!=0) || year%400 == 0){ //http://stackoverflow.com/questions/1021324/java-code-for-calculating-leap-year
            return true;
        }
        return false;
    }

    // Main
    /** Create a new DateChecker object and call processDates */
    public static void main(String[] arguments){
        DateChecker dc = new DateChecker();
        dc.processDates();
    }        

}
