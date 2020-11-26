<?php include 'cons.php' ?>
<?php
if(isset ($_GET['data'])){
$data = $_GET['data'];
$qstring ="select  word from words where word  REGEXP '^" . $data . "$';";
$result = mysqli_query($con,$qstring) or die (mysql_error());
echo "<words>\n";
while( $row = mysqli_fetch_array($result) ){

echo( "<word><value>" . $row[0] . "</value></word>\n");

}
echo "</words>\n";
mysqli_free_result($result);
mysqli_close($con);
}
?>
