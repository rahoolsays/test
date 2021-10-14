<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title></title>

<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
  <style>
    table, td, div, h1, p {
      font-family: Arial, sans-serif;
      font-size: 15px;
    }

ul.social-network {
	list-style: none;
	display: inline;
	margin-left:0 !important;
	padding: 0;
}
ul.social-network li {
	display: inline;
	margin: 0 5px;
}
.social-network a.ico {
	background-color: #808282;
}

.social-circle li a {
	display:inline-block;
	position:relative;
	margin:0 auto 0 auto;
	-moz-border-radius:50%;
	-webkit-border-radius:50%;
	border-radius:50%;
	text-align:center;
	width: 50px;
	height: 50px;
	font-size:20px;
}
.social-circle li i {
	margin:0;
	line-height:50px;
	text-align: center;
  color: white;
}

    @media screen and (max-width: 530px) {
      .unsub {
        display: block;
        padding: 8px;
        margin-top: 14px;
        border-radius: 6px;
        background-color: #555555;
        text-decoration: none !important;
        font-weight: bold;
      }
      .col-lge {
        max-width: 100% !important;
      }
    }
    @media screen and (min-width: 531px) {
      .col-sml {
        max-width: 27% !important;
      }
      .col-lge {
        max-width: 73% !important;
      }

    }
  </style>
</head>
<body style="margin:0;padding:0;word-spacing:normal;background-color:#efefef">
  <div role="article" aria-roledescription="email" lang="en" style="text-size-adjust:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;">
    <table role="presentation" style="width:100%;border:none;border-spacing:0;text-align:left;font-family:Arial,sans-serif;font-size:16px;line-height:22px;color:#363636;">

      <tr>
        <td style="padding:20px;border-bottom:1px solid#f0f0f5;border-color:rgba(201,201,207,.35);">

          <div style="max-width:740px;background-color:white;vertical-align:top;font-family:Arial,sans-serif;font-size:16px;line-height:22px;color:#363636;margin: auto;box-shadow: 0px 0px 10px 0px rgb(0 0 0 / 9%);">

            <div style="padding:30px;border-bottom:1px solid#808282;padding-bottom: 10px;margin-bottom:20px;">

          <img src="https://i.ibb.co/NTH5s1G/logo-blue.jpg" alt="logo-blue" style="width:240px;">
            </div>

              <div style="padding:30px;">


                <p style="margin-top:0;margin-bottom:18px;">
                  Hi,
                </p>

                <p style="margin-top:0;margin-bottom:18px;">
                 Hope you are doing great!
                </p>

                <p style="margin-top:0;margin-bottom:18px;">
                  After detailed review  of information provided by you as part of being live with simpaisa, we are sorry say we can not got live with you right now.

                </p>
                <p style="margin-top:0;margin-bottom:18px;">
                     Following is reason mentioned by our team, please provide following requirements and we will review and hopefully approve them
                     Please note that your form is editable again.
                 </p>
                  <p style="margin-top:0;margin-bottom:18px;">
                  ${rejectionReason}
                  </p>

                <p style="margin-top:50px;margin-bottom:18px;">
                  Best regards,
                </p>
                <p style="margin:0;font-weight:600;">
                  Simpaisa Team
                </p>
                <p style="margin:0;color:#808282;">
                  Customer Relationship Team Lead
                </p>
              </div>
              <div style="background:#44619e;padding: 20px 0 20px 0px;">
                <p style="margin-top:0;margin-bottom:18px;text-align:center;color:white;">
                  &copy; 2021 Simpaisa, All rights reserved
                </p>
                <div style=" margin: auto; text-align: center; ">
                  <ul class="social-network social-circle">
                    <li><a href="#" class="ico" title="Facebook"><i class="fa fa-facebook"></i></a></li>
                    <li><a href="#" class="ico" title="Linkedin"><i class="fa fa-linkedin"></i></a></li>
                    <li><a href="#" class="ico" title="Instagram"><i class="fa fa-instagram"></i></a></li>
                  </ul>
                </div>

              </div>
          </div>
        </td>
      </tr>
    </table>
  </div>
</body>
</html>