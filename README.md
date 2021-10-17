# razorpay_test_paymentid_automation
Automation related to Payment needs valid paymentId to be generated, <br> 
so this example will make use of razorpay java dependency and will try to do payment checkout.<br>

# Available APIs
## Generate valid payment ID
```
curl --location --request GET 'http://localhost:9192/api/get_valid_payment_id'
```

## Generate invalid payment ID
```
curl --location --request GET 'http://localhost:9192/api/get_invalid_payment_id'
```

