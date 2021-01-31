<?php
   $noti_db = mysqli_connect("localhost","admin","Pyro*0085","anplatra");
   $noti_query = "SELECT * FROM notifications";
   $noti_exec = mysqli_query($noti_db,$noti_query);
   if(mysqli_num_rows($noti_exec) > 0){
      while($row = mysqli_fetch_assoc($noti_exec)){
        echo $row['notification'];
        echo ";";
     } 
   }
?>
