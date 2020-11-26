<?php include 'cons.php' ?>
<?php
//if(isset ($_GET['data']) && isset ($_GET['op']) && isset ($_GET['size'])){
if(isset ($_GET['data']) && isset ($_GET['size']) && isset ($_GET['op'])){
$data = $_GET['data'];
$siz =  $_GET['size'];
$op = $_GET['op'];
$qstring ="SELECT  word from words";

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
