package features;


import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.http.ParseException;

import java.util.concurrent.TimeUnit.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

//import org.apache.log4j.Logger;


public class CustomReport {
    private static int m_count = 0;

    static List<String> a = new ArrayList<String>();
    static List<String> steps = new ArrayList<String>();
    //public static Logger logger = Logger.getLogger(CustomListener.class);
    static Properties list = new Properties();


    public static void generateReport() {

        String Project_Name = "FL Tolling";
        String Release_Version = "Q1 ";
        String URL = "10.36.20.4/callcenter_enu/start.swe";
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Date dateobj = new Date();
        String Test_Date = df.format(dateobj);
        String Start_Time = df.format(dateobj);
        String End_Time = df.format(dateobj);
        System.out.println(df.format(dateobj));
        int totalpass = 0, totalfail = 0, totalskipped = 0, totalmethods = 0;
        float passper = 0;
        float failper = 0;
        float skipper = 0;
        String Duration = " ";
        String Browser_Type = "Chrome";
        String Executed_By = System.getProperty("user.name");
        String OS_Type = System.getProperty("os.name");
        String colorcode = "green";
        DecimalFormat dff = new DecimalFormat("##.##");
        try {
            for (int i = 0; i < a.size()-1; i++) {
                String x[] = a.get(i).split("#");
                if (x[1].equalsIgnoreCase("PASS"))
                    totalpass++;
                else if (x[1].equalsIgnoreCase("FAIL"))
                    totalfail++;
                else
                    totalskipped++;
            }
            // totalpass=3;
            // totalfail=3;
            // totalskipped=3;
            totalmethods = totalpass + totalfail + totalskipped;
            Date date = new Date();
            Test_Date = df.format(date);
            DateFormat df1 = new SimpleDateFormat("HH:mm:ss:SSS");
            Start_Time = df1.format(date);

            End_Time = df1.format(date);


            Long time11 = Long.valueOf(85868);
            Duration = DurationFormatUtils.formatDuration(time11, "HH:mm:ss.SSS");

            int xx = totalpass + totalskipped;
            if (totalfail > xx) {
                colorcode = "red";
                System.out.println("hello");
            }

            if (totalskipped > (totalfail + totalpass)) {
                colorcode = "#FF4545";
            }

            String str1 = String.valueOf(GetCustomPieChartFailDegree(totalpass, totalmethods));
            String str2 = String.valueOf(GetCustomPieChartFailDegree(totalfail, totalmethods));
            String str3 = String.valueOf(GetCustomPieChartFailDegree(totalskipped, totalmethods));
            String str4 = String.valueOf(GetCustomPieChartFailDegree(totalpass, totalmethods) + GetCustomPieChartFailDegree(totalfail, totalmethods));

            System.out.println(str1 + "  " + str2 + "  " + str3 + "  " + str4);

            if (totalpass != 0) {
                passper = getPercentageCorrect(totalpass, totalmethods);
            }
            if (totalfail != 0) {
                failper = getPercentageCorrect(totalfail, totalmethods);
            }
            if (totalskipped != 0) {
                skipper = getPercentageCorrect(totalskipped, totalmethods);
            }

            StringBuilder htmlStringBuilder = new StringBuilder();
            //append html header and title
            String projectPath = System.getProperty("user.dir");
            String tempFile = projectPath + File.separator + "conduent.png";
            System.out.println(projectPath);
            String extent1 = projectPath + File.separator + "featherlight.min.css";
            String extent2 = projectPath + File.separator + "featherlight.min.js";
            String extent3 = projectPath + File.separator + "bootstrap.min.js";
            String extent4 = projectPath + File.separator + "bootstrap.min.css";
            htmlStringBuilder.append("<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<title>"
                    + "Test Execution results"
                    + "</title>"
                    +"<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js\"></script>"
                    + "<script>"
                    + "function expandClose(obj){"
                    + "if(obj==true){"
                    + "document.getElementById('TblResult').style.display='';"
                    + "document.getElementById('plus').style.display='none';"
                    + "document.getElementById('minus').style.display='inline';"
                    + "return false;"
                    + "}"
                    + "else{"
                    + "document.getElementById('TblResult').style.display='none';"
                    + "document.getElementById('plus').style.display='';"
                    + "document.getElementById('minus').style.display='none';"
                    + "return false;"
                    + "}"
                    + "}"
                    + "</script>"
                    + "<SCRIPT> $(function() {  $('.nishanth').on('click', function() {  $('.imagepreview').attr('src', $(this).find('img').attr('src'));  $('#imagemodal').modal('show');    }); }); </SCRIPT>      <script src=" + extent2 + " type=\"text/javascript\" charset=\"utf-8\"></script>"
                    + "<SCRIPT TYPE=\"text/javascript\"> function popup(mylink, windowname) { if (! window.focus)return true; var href; if (typeof(mylink) == 'string') href=mylink; else href=mylink.href; window.open(href, windowname, 'width=600,height=300,left=100,top=100,resizable=yes,scrollbars=yes'); return false; }</script>"
                    +"<script\n" +
                    "    src=\"http://maps.googleapis.com/maps/api/js?key=YOUR_APIKEY&sensor=false\">\n" +
                    "</script>"
                    + "<link href=" + extent1 + " rel='stylesheet'>  <link href=" + extent3 + " rel='stylesheet'> <link href=" + extent4 + " rel='stylesheet'>  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">  <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">   <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>"
                    + "</head>"


                    + "<style>"
                    + ".pieContainer {"
                    + "height: 100px;"
                    + "}"

                    + ".pieBackground {"
                    + "background-color:" + colorcode + ";"
                    + "position: absolute;"
                    + "width: 100px;"
                    + "height: 100px;"
                    + "-moz-border-radius: 50px;"
                    + "-webkit-border-radius: 50px;"
                    + "-o-border-radius: 50px;"
                    + "border-radius: 50px;"
                    + "-moz-box-shadow: -1px 1px 3px #000;"
                    + "-webkit-box-shadow: -1px 1px 3px #000;"
                    + "-o-box-shadow: -1px 1px 3px #000;"
                    + "box-shadow: -1px 1px 3px #000;"
                    + "}"

                    + ".pie {"
                    + "position: absolute;"
                    + "width: 100px;"
                    + "height: 100px;"
                    + "-moz-border-radius: 50px;"
                    + "-webkit-border-radius: 50px;"
                    + "-o-border-radius: 50px;"
                    + "border-radius: 50px;"
                    + "clip: rect(0px, 50px, 100px, 0px);"
                    + "}"

                    + ".hold {"
                    + "position: absolute;"
                    + "width: 100px;"
                    + "height: 100px;"
                    + "-moz-border-radius: 50px;"
                    + "-webkit-border-radius: 50px;"
                    + "-o-border-radius: 50px;"
                    + "border-radius: 50px;"
                    + "clip: rect(0px, 100px, 100px, 50px);"
                    + "}"
                    //  + ".hold.gt50 {    clip:rect(auto, auto, auto, auto); 		            	   }
                    + " #pieSlice1 .pie { background-color: green; -webkit-transform:rotate(" + str1 + "deg); -moz-transform:rotate(" + str1 + "deg); -o-transform:rotate(" + str1 + "deg); transform:rotate(" + str1 + "deg); } "
                    + "#pieSlice2 {"
                    + "-webkit-transform:rotate(" + str1 + "deg);"
                    + "-moz-transform:rotate(" + str1 + "deg);"
                    + "-o-transform:rotate(" + str1 + "deg);"
                    + "transform:rotate(" + str1 + "deg);"
                    + "}"

                    + "#pieSlice2 .pie {"
                    + "background-color: #FF0000;"
                    + "-webkit-transform:rotate(" + str2 + "deg);"
                    + "-moz-transform:rotate(" + str2 + "deg);"
                    + "-o-transform:rotate(" + str2 + "deg);"
                    + "transform:rotate(" + str2 + "deg);"
                    + "}"

                    + "#pieSlice3 {"
                    + "-webkit-transform:rotate(" + str4 + "deg);"
                    + "-moz-transform:rotate(" + str4 + "deg);"
                    + "-o-transform:rotate(" + str4 + "deg);"
                    + "transform:rotate(" + str4 + "deg);"
                    + "}"

                    + "#pieSlice3 .pie {"
                    + "background-color: #FF4545;"
                    + "-webkit-transform:rotate(" + str3 + "deg);"
                    + "-moz-transform:rotate(" + str3 + "deg);"
                    + "-o-transform:rotate(" + str3 + "deg);"
                    + "transform:rotate(" + str3 + "deg);"
                    + "}"


                    + "th.header {"
                    + "font: bold 16px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "}"

                    + "th {"
                    + "background: #4f6b72 url(images/bg_header.jpg) no-repeat scroll 0 0;"
                    + "border: 1px solid #c1dad7;"
                    + "color: #ffffff;"
                    + "font: bold 11px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "letter-spacing: 2px;"
                    + "padding: 6px 6px 6px 12px;"
                    + "text-align: center;"
                    + "text-transform: uppercase;"
                    + "}"

                    + "td {"
                    + "background: #ffffff none repeat scroll 0 0;"
                    + "border: 1px solid #c1dad7;"
                    + "color: #4f6b72;"
                    + "padding: 1px;"
                    + "}"

                    + "td.Status {"
                    + "color: #088a4b;"
                    + "font: bold 12px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "text-align: center;"
                    + "}"

                    + "td.alt {"
                    + "background: #c1dad7 none repeat scroll 0 0;"
                    + "color: #000000;"
                    + "font: bold 12px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "text-align: center;"
                    + "text-transform: uppercase;"
                    + "}"


                    + "td.TotalCount {"
                    //+ "background: #00ced1  none repeat scroll 0 0;"
                    + "background: #FF8C00 none repeat scroll 0 0;"
                    + "border-bottom: 1px solid #c1dad7;"
                    + "border-right: 1px solid #c1dad7;"
                    + "color: #ffffff;"
                    + "font: bold 15px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "padding: 1px;"
                    + "text-align: center;"
                    + "text-transform: uppercase;"
                    + "}"

                    + "td.PassCount {"
                    + "background: #088a4b none repeat scroll 0 0;"
                    + "border-bottom: 1px solid #c1dad7;"
                    + "border-right: 1px solid #c1dad7;"
                    + "color: #ffffff;"
                    + "font: bold 15px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "padding: 1px;"
                    + "text-align: center;"
                    + "text-transform: uppercase;"
                    + "}"

                    + "td.FailCount {"
                    + "background: #FF0000 none repeat scroll 0 0;"
                    + "border-bottom: 1px solid #c1dad7;"
                    + "border-right: 1px solid #c1dad7;"
                    + "color: #ffffff;"
                    + "font: bold 15px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "padding: 1px;"
                    + "text-align: center;"
                    + "text-transform: uppercase;"
                    + "}"

                    + "td.SkippedCount {"
                    + "background: #FF4545 none repeat scroll 0 0;"
                    + "border-bottom: 1px solid #c1dad7;"
                    + "border-right: 1px solid #c1dad7;"
                    + "color: #ffffff;"
                    + "font: bold 15px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "padding: 1px;"
                    + "text-align: center;"
                    + "text-transform: uppercase;"
                    + "}"

                    + "div.passLegend {"
                    + "background: #228B22 none repeat scroll 0 0;"
                    + "border-bottom: 1px solid #c1dad7;"
                    + "border-right: 1px solid #c1dad7;"
                    + "color: #ffffff;"
                    + "font: bold 13px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "padding: 1px;"
                    + "text-align: center;"
                    //+ "text-transform: uppercase;"
                    + "width:45px"
                    + "}"


                    + "div.failLegend {"
                    + "background: #FF0000 none repeat scroll 0 0;"
                    + "border-bottom: 1px solid #c1dad7;"
                    + "border-right: 1px solid #c1dad7;"
                    + "color: #ffffff;"
                    + "font: bold 13px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "padding: 1px;"
                    + "text-align: center;"
                    //+ "text-transform: uppercase;"
                    + "width:45px"
                    + "}"

                    + "div.skipLegend {"
                    + "background: #FF4545 none repeat scroll 0 0;"
                    + "border-bottom: 1px solid #c1dad7;"
                    + "border-right: 1px solid #c1dad7;"
                    + "color: #ffffff;"
                    + "font: bold 13px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "padding: 1px;"
                    + "text-align: center;"
                    //+ "text-transform: uppercase;"
                    + "width:45px"
                    + "}"

                    + "textarea.failStatus {"
                    + "background: #FF0000 none repeat scroll 0 0;"
                    //    + "color: #ff0000;"
                    + "font: bold 12px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "border-bottom: 1px solid #c1dad7;"
                    + "border-right: 1px solid #c1dad7;"
                    + "color: #ffffff;"
                    + "font: bold 12px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "padding: 1px;"
                    + "text-align: left;"
                    + "}"
                    + "td.failStatus {"
                    //  + "background: #FF0000 none repeat scroll 0 0;"
                    + "color: #ff0000;"
                    + "font: bold 12px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "border-bottom: 1px solid #c1dad7;"
                    + "border-right: 1px solid #c1dad7;"
                    + "color: red;"
                    + "font: bold 12px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "padding: 1px;"
                    + "text-align: center;"
                    + "}"
                    + "td.Skippedstatus {"
                    //  + "background: #FF0000 none repeat scroll 0 0;"
                    + "color: #ff4545;"
                    + "font: bold 12px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "border-bottom: 1px solid #c1dad7;"
                    + "border-right: 1px solid #c1dad7;"
                    + "color: #ff4545;"
                    + "font: bold 12px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;"
                    + "padding: 1px;"
                    + "text-align: center;"
                    + "}"
                    + "td.align123{"
                    + "background: #ffffff none repeat scroll 0 0;"
                    + "border: 1px solid #c1dad7;"
                    + "color: #4f6b72;"
                    + "padding: 1px;"
                    + "}"

                    + "th.Logo{"
                    //+ "background: #87CEEB none repeat scroll 0 0;"
                    + "background: #FFFFFF none repeat scroll 0 0;"
                    + "border: 1px solid #c1dad7;"
                    + "border-right: 1px solid #c1dad7;"
                    + "color: #ffffff;"
                    + "font: bold 13px Trebuchet MS,Verdana,Arial,Helvetica,sans-serif;padding: 1px;"
                    + "text-align: center;"
                    + "width:45px"
                    + "}"

                    + "a {"
                    + "color: #FF8C00;"
                    + "}"
                    + ".report-img {      border: 4px solid #f6f7fa;      cursor: zoom-in;      width: 150px;  }   img {     display: block;      margin-top: 15px;  \tborder: 0;  }"
                    + ". featherlight .featherlight-content {     position: relative;     text-align: left;     vertical-align: middle;     display: inline-block;     overflow: auto;     padding: 25px 25px 0;      border-bottom: 25px solid transparent;      min-width: 30%;      margin-left: 5%;      margin-right: 5%;      max-height: 95%;      background: #fff;      cursor: auto;     white-space: normal;  }"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<center>"
                    + "<table width=810 class=align123 cellspacing=0 cellpadding=0 border=1.5>"
                    + "<tbody>"
                    + "<tr width = 810> "
                    + "<th class=Logo align=center>"
                    + "<img src=\"https://cd.lifeatworkportal.com/static60/resources/adv/bpaas/cdt/images/logo.png\" alt=\"Logo\" width=\"100\" height=\"30/\">"
                    + "</th>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>"
                    + "<table width=810 cellspacing=0 cellpadding=0 border=0>"
                    + "<tbody>"
                    + "<th class=header align=center font face=verdana>TEST EXECUTION REPORT</th>"
                    //+           "<br>"
                    //+           "<center>"
                    + "<table width=810 cellspacing=0 cellpadding=0 border=0>"
                    + "<tr>"
                    + "<th width=15% bgcolor=#D3D3D3>Project Name:</th>"
                    + "<td width=35% align=center colspan=1>" + Project_Name + "</td>"
                    + "<th width=15% bgcolor=#D3D3D3>Release Version:</th>"
                    + "<td id=testDate width=35% align=center colspan=3>" + Release_Version + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<th width=15% bgcolor=#D3D3D3>URL:</th>"
                    + "<td width=35% align=center colspan=1>" + URL + "</td>"
                    + "<th width=15% bgcolor=#D3D3D3>Test Date:</th>"
                    + "<td id=testDate width=35% align=center colspan=3>" + Test_Date + "</td>"
                    + "</tr>"

                    + "<tr>"
                    + "<th width=19% bgcolor=#D3D3D3>Test Start Time:</th>"
                    + "<td id=testStartTime width=38% align=center>" + Start_Time + "</td>"
                    + "<th width=17% bgcolor=#D3D3D3>Test End Time:</th>"
                    + "<td align=center colspan=3>" + End_Time + "</td>"
                    + "</tr>"

                    + "<tr>"
                    + "<th width=19% bgcolor=#D3D3D3>Duration:</th>"
                    + "<td id=testStartTime width=38% align=center>" + Duration + "</td>"
                    + "<th width=17% bgcolor=#D3D3D3>Browser Type:</th>"
                    + "<td align=center colspan=3>" + Browser_Type + "</td>"
                    + "</tr>"

                    + "<tr>"
                    + "<th width=15% bgcolor=#D3D3D3>Executed By :</th>"
                    + "<td id=timeDur width=35% align=center colspan=1>" + Executed_By + "</td>"
                    + "<th width=15% bgcolor=#D3D3D3>OS Type :</th>"
                    + "<td id=testedBy width=35% align=center colspan=3>" + OS_Type + "</td>"
                    + "</tr>"
                    + "</tbody>"
                    + "</table>"

                    + "<br>"
                    + "<center>"
                    + "<table width=810 cellspacing=0 cellpadding=0 border=0>"
                    + "<tbody>"
                    + "<tr>"
                    + "<th class=header width=100% colspan=4 bgcolor=#AFEEEE> SUMMARY REPORT </th>"
                    + "</tr>"
                    + "<tr>"
                    + "<td class=alt align=center width=25% bgcolor=#FFF5EE>Total Test Cases</td>"
                    + "<td class=alt align=center width=25% bgcolor=#FFF5EE>Test Cases Passed</td>"
                    + "<td class=alt align=center width=25% bgcolor=#FFF5EE>Test Cases Failed</td>"
                    + "<td class=alt align=center width=25% bgcolor=#FFF5EE>Test Cases Skipped</td>"
                    //+ "<td class=alt align=center width=25% bgcolor=#FFF5EE>Pass Percentage</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td class=TotalCount width=25% align=center>" + totalmethods + "</td>"
                    + "<td class=PassCount width=25% align=center bgcolor=#228B22>" + totalpass + "</td>"
                    + "<td class=FailCount width=25% align=center bgcolor=#FF4500>" + totalfail + "</td>"
                    + "<td class=SkippedCount width=25% align=center bgcolor=#FF4500>" + totalskipped + "</td>"
                    //+ "<td class=Percentage width=25% align=center bgcolor=#C0C0C0>{11}</td>"
                    + "</tr>"
                    + "</tbody>"
                    + "</table>"

                    + "<br>"
                    + "<center>"
                    + "<table width=800 cellspacing=0 cellpadding=0 border=0>"
                    + "<tbody >"
                    + "<tr>"
                    + "<div>"
                    + "<div style=margin-left:43%>"
                    + "<div class=pieContainer>"
                    + "<div class=pieBackground align=right></div>"
                    + "<div id=pieSlice1 class=hold align = right><div class=pie align = right></div></div>"
                    + "<div id=pieSlice2 class=hold align = right><div class=pie></div></div>"
                    + "<div id=pieSlice3 class=hold align = right><div class=pie></div></div>"
                    + "<br>"
                    + "<br>"
                    + "<br>"
                    + "<div style=margin-right:10%>"
                    + "<div class=passLegend>" + dff.format(passper) + "%</div>"
                    + "<div class=failLegend>" + dff.format(failper) + "%</div>"
                    + "<div class=skipLegend>" + dff.format(skipper) + "%</div>"
                    //  +                         "<div style=display:block;background-color:green;width:52px>{11}</div>"
                    //  +                         "<br>"
                    //  +                         "<div style=display:block;background-color:red;width:52px>{12}</div>"
                    + "</div>"
                    + "</div>"
                    + "</tr>"
                    + "</tbody>"
                    + "</table>"
                    + ""
                    + "<Div color='#ff0000'> Please Click On TestCase Name For Detailed Report<sup style='color:red;'>*</sup></div><br><center>"
                    + "<table width=810 cellspacing=0 cellpadding=0 border=0>"
                    + "<tbody>"
                    + "<tr>"
                    + "<th class=header width=100% colspan=4 bgcolor=#AFEEEE>"
                    + "DETAILED REPORT <a id='plus' href='' onclick='return expandClose(true);' > <font color=#FF8C00> [SHOW ALL] </font> </a>"
                    + "<a id='minus' href='' onclick='return expandClose(false);' style='display:none' > <font color=#FF8C00> [HIDE]</font> </a>"
                    + "</th>"
                    + "</tr>"
                    + "<tr cellspacing=0 cellpadding=0 border=0>"
                    + "<td colspan=4 cellspacing=0 cellpadding=0 border=0>"
                    + "<table id='TblResult' width=100% style='display:none' cellspacing=0 cellpadding=0 border=0>"
                    + "<tr cellspacing=0 cellpadding=0 border=0>"
                    + "<td class=alt align=center width=40% bgcolor=#FFF5EE>Test Case Name</td>"
                    + "<td class=alt align=center width=20% bgcolor=#FFF5EE>Status</td>"
                    + "<td class=alt align=center width=20% bgcolor=#FFF5EE>Message</td>"
                    + "<td class=alt align=center width=20% bgcolor=#FFF5EE>Execution Time</td>"
                    + "</tr>");


            for (int i = 0; i < a.size()-1; i++) {
                String x[] = a.get(i).split("#");

                System.out.println("******************");

                tempFile = projectPath + File.separator + "Report\\Suite\\Suite_HTML\\" + x[0].trim() + ".html";
                System.out.println("******************" + tempFile);
                System.out.println(x[1].trim());
                if (x[1].trim().equalsIgnoreCase("PASS"))
                    htmlStringBuilder.append("<tr cellspacing=0 cellpadding=0 border=0><td class=TestCase width=40% align=left><a data-toggle=\"modal\" href=\"#myModal\"> " + x[0].trim() + "</a></td> <td class=Status width=20% align=center bgcolor=#228B22>" + x[1].trim() + "</td> <td class=Status width=20% align=center bgcolor=#228B22>" + x[2].trim() + "</td><td class=Result width=20% align=center bgcolor=#C0C0C0>" + x[3].trim() + " </td></tr>");
                else if (x[1].trim().equalsIgnoreCase("FAIL"))
                    htmlStringBuilder.append("<tr cellspacing=0 cellpadding=0 border=0><td class=TestCase width=40% align=left><a data-toggle=\"modal\" href=\"#myModal\"> " + x[0].trim() + "</a></td><td class=failStatus align=center color=red width=20% >" + x[1].trim() + "</td><td class=failStatus align=center color=red width=20% ><textarea class=failStatus readonly=true cols=35>" + x[2].trim() + "</textarea></td>  <td class=Result width=20% align=center bgcolor=#C0C0C0>" + x[3].trim() + " </td></tr>");
                else
                    htmlStringBuilder.append("<tr cellspacing=0 cellpadding=0 border=0><td class=TestCase width=40% align=left><a data-toggle=\"modal\" href=\"#myModal\"> " + x[0].trim() + "</a></a></td><td class=Skippedstatus align=center color=red width=20% >" + x[1].trim() + "</td><td class=Skippedstatus align=center color=#ff4545 width=20% ><textarea class=failStatus readonly=true cols=35>" + x[2].trim() + "</textarea></td>  <td class=Result width=20% align=center bgcolor=#C0C0C0>" + x[3].trim() + " </td></tr>");


            }
            htmlStringBuilder.append("</table>");
            htmlStringBuilder.append("<div class=\"modal fade\" id=\"myModal\" role=\"dialog\">      <div class=\"modal-dialog\" style=\"width:1000px;\" >    <div class=\"modal-content\">    <div class=\"modal-header\">   <button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>   <h4 class=\"modal-title\">Stepwise Details</h4>  </div>    <div class=\"modal-body\">");
            htmlStringBuilder.append("<table width='800' align='center'>  <thead>  <tr> <td>Status</td><td>  Step Name</td><td>  Details   </td><td></td> </tr> </thead> <tbody>");
            for (int i = 0; i < steps.size(); i++) {

                // String pop="pop";
                String x[] = steps.get(i).split("#");
                if (x[0].trim().equalsIgnoreCase("FAIL")) {
                    if (!x[3].trim().equalsIgnoreCase("NA"))
                        htmlStringBuilder.append("<tr><td align=\"center\" class='status fail' title='fail' alt='fail'><i class=\"fa fa-times\" style=\"font-size:20px;color:red\"></i></i></td> <td>" + x[1].trim() + "</td> <td>" + x[2].trim() + "</td> <td><a href=\"#\" class='nishanth'><img class='report-img' src=" + x[3].trim() + " />  </a> </td></tr>");
                    else
                        htmlStringBuilder.append("<tr><td align=\"center\" class='status fail' title='fail' alt='fail'><i class=\"fa fa-times\" style=\"font-size:20px;color:red\"></i></i></td> <td>" + x[1].trim() + "</td> <td colspan='2'>" + x[2].trim() + "  </td></tr>");
                } else if (x[0].trim().equalsIgnoreCase("PASS")) {
                    if (!x[3].trim().equalsIgnoreCase("NA"))
                        htmlStringBuilder.append("<tr><td align=\"center\" class='status pass' title='pass' alt='pass'><i class=\"fa fa-check-circle\" style=\"font-size:20px;color:green\"></i></td> <td>" + x[1].trim() + "</td> <td>" + x[2].trim() + "</td> <td><a href=\"#\" class=\"nishanth\"><img class='report-img' src=" + x[3].trim() + " />  </a> </td></tr>");
                    else
                        htmlStringBuilder.append("<tr><td align=\"center\" class='status pass' title='pass' alt='pass'><i class=\"fa fa-check-circle\" style=\"font-size:20px;color:green\"></i></td></td> <td>" + x[1].trim() + "</td> <td colspan='2'>" + x[2].trim() + "  </td></tr>");
                } else {
                    if (!x[3].trim().equalsIgnoreCase("NA"))
                        htmlStringBuilder.append("<tr><td align=\"center\" class='status info' title='info' alt='info'><i class='fa fa-info-circle'style=\"font-size:20px;color:#99e6ff\"></i></td> <td>" + x[1].trim() + "</td> <td>" + x[2].trim() + "</td> <td><a href=\"#\" class=\"nishanth\"><img class='report-img' src=" + x[3].trim() + "  style='width: 80px; height: 60px;'/>   </a> </td></tr>");
                    else
                        htmlStringBuilder.append("<tr><td align=\"center\" class='status info' title='info' alt='info'><i class='fa fa-info-circle'style=\"font-size:20px;color:#99e6ff\"></i></td> <td>" + x[1].trim() + "</td> <td colspan='2'>" + x[2].trim() + "  </td></tr>");


                }
            }
            htmlStringBuilder.append("</table>");
            htmlStringBuilder.append("  </div>    <div class=\"modal-footer\">  <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close</button>       </div>   </div>     </div>    </div>");
            htmlStringBuilder.append(
                    "<div class=\"modal fade\" id=\"imagemodal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\n" +
                            "  <div class=\"modal-dialog\"  style=\"width:1000px;\">" +
                            "    <div class=\"modal-content\" >             " +
                            "      <div class=\"modal-body\">" +
                            "      \t<button type=\"button\" class=\"close\" data-dismiss=\"modal\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>\n" +
                            "        <img src=\"\" class=\"imagepreview\" style=\"height: 500px; width: 1000px; \">" +
                            "      </div>\n" +
                            "    </div>\n" +
                            "  </div>\n" +
                            "  </div>");
            htmlStringBuilder.append("</body></html>");
            //write html string content to a file
            WriteToFile(htmlStringBuilder.toString(), "testfile.html");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void WriteToFile(String fileContent, String fileName) throws IOException {
        String projectPath = System.getProperty("user.dir");
        String tempFile = projectPath + File.separator + fileName;
        File file = new File(tempFile);

        if (file.exists()) {
            try {
                File newFileName = new File(projectPath + File.separator + "backup_" + fileName);
                file.renameTo(newFileName);
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //write to file with OutputStreamWriter
        OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
        Writer writer = new OutputStreamWriter(outputStream);
        writer.write(fileContent);
        writer.close();

        sendmail1();

    }


    public static void log(String string) {
        System.out.println(string);
        a.add(string);


        m_count++;
        if (m_count % 40 == 0) {
            System.out.println("");
        }
    }

    public static void addStep(String data) {
        steps.add(data);
        System.out.println(data);
    }

    public static int GetCustomPieChartFailDegree(int pCount, int tCount) {
        int degree1 = 0;
        try {
            int fCount = tCount - pCount;
            int degree2 = 360 / tCount;
            degree1 = pCount * degree2;

        } catch (Exception Ex) {
            System.out.println(Ex.getMessage());
        }
        return degree1;
    }

    public static String formatTimeUnit(long millis) throws ParseException {
        String formatted = String.format(
                "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis)
                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return formatted;
    }

    public static float getPercentageCorrect(int correct, int questions) {
        float proportionCorrect = ((float) correct) / ((float) questions);
        return proportionCorrect * 100;
    }

    public static void loadPropertyFile(File file) {
        try {

            FileInputStream fis = new FileInputStream(file);
            list.load(fis);
        } catch (IOException e) {
            //logger.error(e.getMessage());
        }
    }

    public static void sendmail() throws IOException {

        try {
            loadPropertyFile(new File(System.getProperty("user.dir").concat("/Property file/mailConfig.properties")));

            final String username = list.getProperty("username");
            final String password = list.getProperty("password");

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", list.getProperty("smtp_Host"));
            props.put("mail.smtp.port", list.getProperty("port"));

            Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });


            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(list.getProperty("from_address")));
            //  message.setRecipients(Message.RecipientType.TO,
            // InternetAddress.parse(list.getProperty("to_address")));

            String mailAddressTo1 = list.getProperty("to_address");
            String[] mailAddressTo = mailAddressTo1.split(",");
            //  String[] mailAddressTo={list.getProperty("to_address") ; //can put multiple receivers in the array

            InternetAddress[] mailAddress_TO = new InternetAddress[mailAddressTo.length];
            for (int i = 0; i < mailAddressTo.length; i++) {
                mailAddress_TO[i] = new InternetAddress(mailAddressTo[i]);
            }


            message.addRecipients(Message.RecipientType.TO, mailAddress_TO);
            message.setSubject(list.getProperty("project_name") + " Automation status Report");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("Hi Team, \n Please find the " + list.getProperty("project_name") + " automation status as an attachment \n\n Thanks \n " + list.getProperty("executed_by") + "");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            //  String filename = System.getProperty("user.dir").concat("/Report.html");
            String filename = "testfile.html";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            // Send message
            Transport.send(message);

            System.out.println("Mail sent successfully");
            //  logger.info("Mail sent successfully");

        } catch (MessagingException e) {
            // logger.error(e.getMessage());
        }

    }


    public static void sendmail1() throws IOException
    {
         final String username = "bharath.gajulapalli@conduent.com";
            final String password = "Jun@2017";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.office365.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("bharath.gajulapalli@conduent.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse("bharath.gajulapalli@conduent.com"));
                message.setSubject("Florida Tolling Automation Test Execution Report");



                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText("Hi team ,"
                        + "\n\n Please find the attachment for Test Execution Report \n  This is autoGenerated Test Execution Report \n\n\n Please Dont Reply to this mail!"

                        + "Regards \n Bharath Kumar G " );

                // Now set the actual message
                //messageBodyPart.setText("Hi Team, \n Please find the automation status as an attachment \n\n Thanks \n " );

                // Create a multipar message
                Multipart multipart = new MimeMultipart();

                // Set text message part
                multipart.addBodyPart(messageBodyPart);

                // Part two is attachment
                messageBodyPart = new MimeBodyPart();
                String filename = System.getProperty("user.dir").concat("/testfile.html");
                System.out.println(filename);
               // String filename = "testfile.html";
                DataSource source = new FileDataSource(filename);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(filename);
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);

                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
    }
}


