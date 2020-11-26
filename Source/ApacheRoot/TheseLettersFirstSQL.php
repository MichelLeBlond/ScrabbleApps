<?php include 'cons.php' ?>
<?php
if(isset ($_GET['data']) && isset ($_GET['op']) && isset ($_GET['size'])){
$data = $_GET['data'];
$op = $_GET['op'];
$siz = $_GET['size'];
// three different queries based on browser variables
if($op == "=") {
$result = mysqli_query($con,"Select word from words
WHERE LOCATE('$data', word) =1
AND CHARACTER_LENGTH(word) = $siz;") or die (mysqli_error()); }

if($op == ">") {
$result = mysqli_query($con,"Select word from words
WHERE LOCATE('$data', word) =1
AND CHARACTER_LENGTH(word) > $siz;") or die (mysqli_error()); }

if($op == "<") {
$result = mysqli_query($con,"Select word from words
WHERE LOCATE('$data', word) =1
AND CHARACTER_LENGTH(word) < $siz;") or die (mysqli_error()); }

/* Fetch the results of the query */

echo "<words>\n";
while( $row = mysqli_fetch_array($result) ){

echo( "<word><value>" . $row[0] . "</value></word>\n");

}
echo "</words>\n";
/* Destroy the result set and free the memory used for it */
mysqli_free_result($result);


/* Close the connection */
mysqli_close($con);
}
?>
