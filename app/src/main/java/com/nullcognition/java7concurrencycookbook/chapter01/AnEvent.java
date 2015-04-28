package com.nullcognition.java7concurrencycookbook.chapter01;

import java.util.Date;

/**
 * Created by ersin on 28/04/15 at 12:29 PM
 */
public class AnEvent {

   public Date   date;
   public String string;

   AnEvent(Date inDate, String fromThread){

	  date = inDate;
	  string = fromThread;
   }

}
