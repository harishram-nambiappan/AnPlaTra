<?php
  $view_connect = mysqli_connect("localhost","admin","Pyro*0085","anplatra");
  if($view_connect){
    $view_query = "SELECT * FROM events";
    $view_result = mysqli_query($view_connect,$view_query);
    if(mysqli_num_rows($view_result) > 0){
       while($row = mysqli_fetch_assoc($view_result)){
           echo "Name:".$row['Name'].",Date:".$row['Date'].",Source:".$row['Source'].",Destination:".$row['Destination'].",Time:".$row['Time'].",Mode:".$row['Mode'].";";
       }
    }
  }
?>
