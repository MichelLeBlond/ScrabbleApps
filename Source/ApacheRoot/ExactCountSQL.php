
<?php include 'cons.php' ?>
<?php
//if(isset ($_GET['data']) && isset ($_GET['op']) && isset ($_GET['size'])){
if(isset ($_GET['data']) && isset ($_GET['size']) && isset ($_GET['op'])){
$data = $_GET['data'];
$siz =  $_GET['size'];
$op = $_GET['op'];
$qstring ="SELECT  word from words WHERE LENGTH(word) $op $siz";

foreach (count_chars($data, 1) as $i => $val) {
  // echo "There were $val instance(s) of \"" , chr($i) , "\" in the string.\n";
  $qstring = $qstring . " AND LENGTH(word) - LENGTH(REPLACE(word,'" . chr($i) . "', '')) = $val";

}
$result = mysqli_query($con,$qstring) or die (mysqli_error());
echo "<words>\n";
while( $row = mysqli_fetch_array($result) ){

echo( "<word><value>" . $row[0] . "</value></word>\n");

}
echo "</words>\n";
mysqli_free_result($result);
mysqli_close($con);

}
?>
