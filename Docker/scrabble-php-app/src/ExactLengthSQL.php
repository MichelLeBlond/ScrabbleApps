<?php include 'cons.php' ?>
<?php
if( isset ($_GET['size']) && isset ($_GET['op'])){

$siz =  $_GET['size'];
$op = $_GET['op'];
if($siz < 5) {
$siz =  $_GET['size'];
$op = $_GET['op'];
$qstring ="select word from words where LENGTH(word) " . $op . $siz .";";
$result = mysqli_query($qstring) or die (mysql_error());
echo "<words>\n";
while( $row = mysqli_fetch_array($result) ){

echo( "<word><value>" . $row[0] . "</value></word>\n");

}
echo "</words>\n";
mysqli_free_result($result);
mysqli_close();
}
}
?>
