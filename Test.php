<?php

    require(dirname(__FILE__) . '/STREAM.php');

    $sdk = new STREAM("https://api.yigim.az/stream/v1", "en");
    $sdk->setAuthenticationData("PMT", "<YOUR SECRET KEY>");

    //
    // Get category list
    //
    print "\n\n--- CATEGORY LIST ---\n\n";
    $categoryList = $sdk->getCategoryList();
    var_dump($categoryList);

    //
    // Get service list for category->id = 2
    //
    print "\n\n--- SERVICE LIST ---\n\n";
    $serviceList = $sdk->getServiceList(2);
    var_dump($serviceList);

    //
    // Get service with id/alias = 919
    //
    print "\n\n--- SERVICE ---\n\n";
    $service = $sdk->getService(919);
    var_dump($service);

    //
    // Get payment option array for service with id/alias = 919
    //
    print "\n\n--- OPTION LIST ---\n\n";
    $optionList = $sdk->getOptionList(919, array("id" => "mikrotik"));
    var_dump($optionList);

    //
    // Registering a payment for service with id/alias = 919
    //
    print "\n\n--- PAYMENT REGISTRATION ---\n\n";
    date_default_timezone_set('Asia/Baku');
    $detailList = array();
    // Let's say customer picked 1-st payment option.
    // We need to take Option->detailList items and send back to 'register' method
    foreach ($optionList[0]->detailList as $item) {
        $detailList[$item->name] = $item->value;
    }
    $reference = $sdk->register(919, 1, 1, 944, "TRN0987654321", date("Y-m-d\TH:i:s"), $detailList, $tag = "tag");
    print "Payment reference: " . $reference;

    //
    // Executing payment by its reference
    //
    print "\n\n--- PAYMENT EXECUTION ---\n\n";
    $payment = $sdk->pay($reference);
    var_dump($payment);

    //
    // Retrieving payment
    //
    print "\n\n--- PAYMENT ---\n\n";
    $payment = $sdk->getPayment($reference);
    var_dump($payment);

    //
    // Retrieving of payment list by specified tag and from/to date bounds
    //
    print "\n\n--- PAYMENT LIST ---\n\n";
    $paymentList = $sdk->getPaymentList("2021-05-30T10:00:00", "2021-05-30T11:00:00");
    var_dump($paymentList);

?>