

import java.text.SimpleDateFormat;
import java.util.*;
import stream.sdk.*;

public class Test {

    public static void main(String[] args) {
        try {
            SDK sdk = new SDK("https://api.yigim.az/stream/v1");
            sdk.setDebug(false);
            sdk.setAuthenticationData("Agent", "Secret Key");
            sdk.setLanguage("en");

            // Get category list
            List<Category> categoryList = sdk.getCategoryList();
            System.out.println("CATEGORY LIST");
            System.out.println("_____________");
            System.out.println();
            for(Category category : categoryList) {
                System.out.println("Category #"    + category.getId());
                System.out.println("ID: "          + category.getId());
                System.out.println("Alias: "       + category.getAlias());
                System.out.println("Name: "        + category.getName());
                System.out.println("Description: " + category.getDescription());
                System.out.println("Order: "       + category.getOrder());
                System.out.println("Empty: "       + category.isEmpty());
                System.out.println("Active: "      + category.isActive());
                System.out.println();
            }
            System.out.println();
            
            // Get service list by category
            List<Service> serviceList = sdk.getServiceList(
                    categoryList.get(0).getId(), // Category ID
                    null,                        // Search keyword
                    0,                           // Offset
                    100                          // Limit
                    );
            System.out.println("SERVICE LIST");
            System.out.println("____________");
            System.out.println();
            for(Service service : serviceList) {
                System.out.println("Service #"     + service.getId());
                System.out.println("ID: "          + service.getId());
                System.out.println("Category: "    + service.getCategory().getAlias());
                System.out.println("Type: "        + service.getType());
                System.out.println("Alias: "       + service.getAlias());
                System.out.println("Name: "        + service.getName());
                System.out.println("Description: " + service.getDescription());
                System.out.println("Order: "       + service.getOrder());
                System.out.println("Active: "      + service.isActive());
                System.out.println("Currencies: "  + service.getCurrencyList());
                System.out.println("Fields: "      + service.getFieldList());
                System.out.println();
            }
            System.out.println();

            // Get service by id or alias
            Service service = sdk.getService(
                    serviceList.get(0).getAlias()
                    );
            System.out.println("SERVICE #: " + service.getId());
            System.out.println();
            System.out.println();

            // Retrieving option list
            Map<String, Object> data = new HashMap();
            data.put("id", "000000");
            List<Option> optionList = sdk.getOptionList(service.getId(), data);
            System.out.println("OPTION LIST");
            System.out.println("-----------");
            System.out.println();
            for(Option option : optionList) {
                System.out.println("Option #"   + option.getName());
                System.out.println("Amount:"    + option.getAmount());
                System.out.println("Currency:"  + option.getCurrency());
                for(Detail detail : option.getDetailList()) {
                    System.out.println("|-- Detail [" + detail.getName() + ": " + detail.getValue() + "]");
                }
                System.out.println();
            }
            System.out.println();

            // Register payment using 1-st option (passing its detals to register)
            data = new HashMap<>();
            for (Detail detail : optionList.get(0).getDetailList()) {
                data.put(detail.getName(), detail.getValue());
            }
            String reference = sdk.register(
                    String.valueOf(service.getId()),
                    10,
                    0,
                    944,
                    "1231231",
                    new Date(),
                    Channel.Kiosk,
                    Source.Card,
                    "test",
                    null,
                    data
            );
            System.out.println("REGISTERED PAYMENT");
            System.out.println("------------------");
            System.out.println();
            System.out.println("Reference: " + reference);
            System.out.println();
            System.out.println();

            // Exeute pay
            Payment payment = sdk.pay(reference);
            System.out.println("EXECUTED PAYMENT");
            System.out.println("----------------");
            System.out.println();
            System.out.println("Payment #"       + payment.getId());
            System.out.println("Reference: "     + payment.getReference());
            System.out.println("Transaction ID"  + payment.getTransactionId());
            System.out.println("Amount"          + payment.getAmount());
            System.out.println("Currency"        + payment.getISO4217());
            System.out.println("Date"            + payment.getDate());
            System.out.println("Origin: "        + payment.getOrigin());
            System.out.println("Serivce: "       + payment.getService().getName());
            System.out.println("Status: "        + payment.getStatus());
            for(Detail detail : payment.getDetailList()) {
                System.out.println("|-- Detail [" + detail.getName() + ": " + detail.getValue() + "]");
            }
            System.out.println();
            System.out.println();
            
            //Get Payment
            payment = sdk.getPayment(payment.getReference());
            System.out.println("RETRIEVED PAYMENT");
            System.out.println("-----------------");
            System.out.println();
            System.out.println("Payment #"       + payment.getId());
            System.out.println("Reference: "     + payment.getReference());
            System.out.println("Transaction ID"  + payment.getTransactionId());
            System.out.println("Amount"          + payment.getAmount());
            System.out.println("Currency"        + payment.getISO4217());
            System.out.println("Date"            + payment.getDate());
            System.out.println("Origin: "        + payment.getOrigin());
            System.out.println("Serivce: "       + payment.getService().getName());
            System.out.println("Status: "        + payment.getStatus());
            for(Detail detail : payment.getDetailList()) {
                System.out.println("|-- Detail [" + detail.getName() + ": " + detail.getValue() + "]");
            }
            System.out.println();
            System.out.println();

            // Get payment list
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            List<Payment> paymentList = sdk.getPaymentList(
                    "tag",
                    sdf.parse("2021-09-20T00:00:00"), // From
                    sdf.parse("2021-09-21T00:00:00"), // To
                    0,   // Offset
                    100 // Limit
                    );
            System.out.println("PAYMENT LIST");
            System.out.println("------------");
            System.out.println();
            System.out.println("Items: " + paymentList.size());
     
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
