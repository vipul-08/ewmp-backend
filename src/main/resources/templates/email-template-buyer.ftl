<html>
<head></head>
<body>
<p> Hi , the following user is registered as buyer . Kindly accept or reject</p>
<p> Serial No. ${serialNo} </p>
<p> Invoice image.  </p>
<img width="640" src=${invoiceImage}></img>
<p style="margin-top:20px"> Hardware Images </p>
<#list hardwareImage as h>
  <img style="margin-top:10px" width="640" src=${h}></img>
</#list>
<p> User id ${userId}</p>
<p>  Extended Warranty Market Place !!!</b></p>
<form method="post" action="${pathToBeCalled}/buyer-verification/verified/${serialNo}/${cartId}">
<input style="margin-top:10px;margin-left:10px" type="submit" value="Verify"> </form>
<form method="post" action="${pathToBeCalled}/buyer-verification/not-verified/${serialNo}/${cartId}">
<input style="margin-top:10px;margin-left:10px" type="submit" value="Decline"> </form>
<p>Thanks</p>
</body>
</html>