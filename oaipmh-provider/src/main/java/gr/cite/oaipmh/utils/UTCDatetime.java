package gr.cite.oaipmh.utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * 
 * @author Ioannis Kavvouras
 *
 */
public class UTCDatetime {
	
	private ZonedDateTime datetime;
	
	/**
	 * 
	 * @param datetime a UTC datetime as string
	 */
	
	/*Year:
	      YYYY (eg 1997)
	   Year and month:
	      YYYY-MM (eg 1997-07)
	   Complete date:
	      YYYY-MM-DD (eg 1997-07-16)
	   Complete date plus hours and minutes:
	      YYYY-MM-DDThh:mmTZD (eg 1997-07-16T19:20+01:00)
	   Complete date plus hours, minutes and seconds:
	      YYYY-MM-DDThh:mm:ssTZD (eg 1997-07-16T19:20:30+01:00)l
	   Complete date plus hours, minutes, seconds and a decimal fraction of a
	second
	      YYYY-MM-DDThh:mm:ss.sTZD (eg 1997-07-16T19:20:30.45+01:00)*/
	public UTCDatetime(String datetime) {
		/*DateTimeFormatter formatter = DateTimeFormatter
		.ofPattern("yyyy")
		.ofPattern("yyyy-MM-dd")
		.ofPattern("yyyy-MM-ddThh:mmTZD")
		.ofPattern("yyyy-MM-ddThh:mm:ssTZD")
		.ofPattern("yyyy-MM-ddThh:mm:ss.sTZD")
		.ofPattern("MMM dd yyyy")
		.ofPattern("MMM dd yyyy hh:mmaa");*/
		
		this.datetime = ZonedDateTime.parse(datetime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		
	}
	
	public UTCDatetime(ZonedDateTime datetime) {
		this.datetime = datetime;
	}
	
	/*private Date dateParser(String date) throws ParseException {
		List<String> formats = ImmutableList.<String> builder().add("MMM dd yyyy hh:mmaa").add("MMM dd yyyy").build();

		for (String format : formats) {
			try {
				return new SimpleDateFormat(format).parse(date);
			} catch (ParseException e) {
			}
		}
		throw new ParseException("Couldn't parse date: '" + date + "' from date formats: " + formats.toString(), 0);
	}*/
	
	public String getDatetimeAsString() {
		return datetime.toString();
	}
	
	public ZonedDateTime getDatetime() {
		return datetime;
	}
	
	/**
	 * 
	 * @return the time now in utc format
	 */
	public static String now() {
		return ZonedDateTime.now(ZoneOffset.UTC).toString();
	}
	
	public boolean isAfter(UTCDatetime datetime) {
		return this.datetime.isAfter(datetime.getDatetime());
	}
	
	public boolean isBefore(UTCDatetime datetime) {
		return this.datetime.isBefore(datetime.getDatetime());
	}
	
	public boolean isEqual(UTCDatetime datetime) {
		return this.datetime.isEqual(datetime.getDatetime());
	}
	
}
