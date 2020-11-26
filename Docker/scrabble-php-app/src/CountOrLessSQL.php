<?php include 'cons.php' ?>
<?php
//if(isset ($_GET['data']) && isset ($_GET['op']) && isset ($_GET['size'])){
if(isset ($_GET['data'])){
$data = $_GET['data'];
$qstring ="SELECT  word from words WHERE LENGTH(word) <=" . strlen($data);

foreach (count_chars($data, 1) as $i => $val) {
  // echo "There were $val instance(s) of \"" , chr($i) , "\" in the string.\n";
  $qstring = $qstring . " AND LENGTH(word) - LENGTH(REPLACE(word,'" . chr($i) . "', '')) <= $val ";

}
$qstring = $qstring . " AND word REGEXP '^[".$data."]{2," . strlen($data) ."}$'";
$result = mysqli_query($con,$qstring) or die (mysql_error());
echo "<words>\n";
while( $row = mysqli_fetch_array($result) ){

echo( "<word><value>" . $row[0] . "</value></word>\n");

}
echo "</words>\n";
mysqli_free_result($result);
mysqli_close($con);
// word REGEXP '^[".$data."]{2," . strlen($data) ."}$' AND"
}


?>
