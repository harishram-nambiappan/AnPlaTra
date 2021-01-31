<?php
   $name = $_POST["name"];
   $date = $_POST["date"];
   $source = $_POST["source"];
   $destination = $_POST["destination"];
   $time = $_POST["time"];
   $mode = $_POST["mode"];
   $event_connect = mysqli_connect("localhost","admin","Pyro*0085","anplatra");
   if($event_connect){
      $enter_query = "INSERT INTO events(Name,Date,Source,Destination,Time,Mode) values('$name','$date','$source','$destination','$time','$mode')";
      $enter_val = mysqli_query($event_connect,$enter_query);
      if($enter_val){
         echo "Data Entered Successfully;";
     }
   }

  $api_anal = exec("python3 /home/pi/anplatra/api_analysis.py '$source' '$destination' '$mode' '$name' '$date'");
  //$api_result = shell_exec($api_anal);
  echo $api_anal;

  $noti_query = "INSERT INTO notifications(notification) values('$api_anal')"; 
  $notify = mysqli_query($event_connect,$noti_query);
  if($notify){
     echo "Notification entered successfully";
  }
?>
