<html>
<head></head>
<body>
<p> Hi , the following user is registered as seller . Kindly accept or reject</p>
<p> GST no. ${gstNo} </p>
<p> Account no. ${accountNo} </p>
<p> IFSC code ${ifscCode} </p>
<p> User id ${userId}</p>
<p> Image url <a href=${imageUrl}> Image Link </a></p>
<p>  Extended Warranty Market Place !!!</b></p>
<form method="get" action="${pathToBeCalled}/seller-verification/verified/${userId}">
<input type="submit" value="Verified"> </form>
<form method="get" action="${pathToBeCalled}/seller-verification/not-verified/${userId}">
<input type="submit" value="Rejected"> </form>
<p>Thanks</p>
</body>
</html>