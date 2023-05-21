package com.example.foodies;// FaqActivity.java

import android.os.Bundle;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FaqActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        RecyclerView faqRecyclerView = findViewById(R.id.faqRecyclerView);
        faqRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        List<FaqItem> faqItems = createFaqItems();
        FaqAdapter faqAdapter = new FaqAdapter(faqItems);
        faqRecyclerView.setAdapter(faqAdapter);
    }

    private List<FaqItem> createFaqItems() {
        List<FaqItem> faqItems = new ArrayList<>();
        faqItems.add(new FaqItem("How can I place an order?", "To place an order, open the app, browse the menu, select the items you want, and proceed to checkout."));
        faqItems.add(new FaqItem("How long does delivery take?", "Delivery times may vary depending on the distance and current demand. Generally, it takes around 30-45 minutes for your order to be delivered."));
        faqItems.add(new FaqItem("Can I track my order?", "Yes, you can track your order in real-time. Once your order is confirmed, you will receive a tracking link or notification to track the progress of your delivery."));
        faqItems.add(new FaqItem("What payment options are available?", "We accept various payment methods, including credit/debit cards, digital wallets, and cash on delivery. Choose the payment option that suits you best."));
        faqItems.add(new FaqItem("What if I have dietary restrictions or allergies?", "If you have any dietary restrictions or allergies, please mention them in the special instructions during the checkout process. We will do our best to accommodate your needs."));
        faqItems.add(new FaqItem("Is there a minimum order value?", "Yes, we have a minimum order value requirement. The minimum order amount is [insert your minimum order value here]."));
        faqItems.add(new FaqItem("How can I contact customer support?", "If you need any assistance, you can contact our customer support team through the app. We are available [mention your customer support timings here]."));
        faqItems.add(new FaqItem("What if I receive a wrong or incomplete order?", "In case of any issues with your order, please contact our customer support immediately. We will resolve the issue and ensure you receive the correct order."));
        faqItems.add(new FaqItem("Can I cancel my order?", "You can cancel your order before it is prepared or dispatched. Once the order is in progress, cancellation may not be possible. Please contact customer support for assistance."));
        faqItems.add(new FaqItem("Do you offer discounts or promotions?", "Yes, we frequently offer discounts, promotions, and special deals. Keep an eye on our app and social media channels to stay updated on the latest offers."));
        faqItems.add(new FaqItem("What are your delivery areas?", "We deliver to a wide range of areas within [insert your delivery areas here]. Enter your address in the app to check if we deliver to your location."));
        faqItems.add(new FaqItem("Can I schedule a delivery for a later time?", "Yes, you can schedule a delivery for a later time. During the checkout process, you will find an option to choose your preferred delivery time."));
        faqItems.add(new FaqItem("Is there a loyalty program?", "Yes, we have a loyalty program. You can earn points for each order and redeem them for discounts or exclusive rewards. Check the app for more details."));
        faqItems.add(new FaqItem("Are there any delivery fees?", "Delivery fees may apply depending on your location and order value. The app will display the delivery fees before you confirm your order."));
        faqItems.add(new FaqItem("What safety measures are taken during delivery?", "We prioritize the safety and hygiene of our customers. Our delivery partners follow strict safety protocols, including contactless delivery and sanitization practices."));
        faqItems.add(new FaqItem("How can I provide feedback or review the app?", "We appreciate your feedback. You can leave a review or provide feedback within the app. Your suggestions help us improve our services."));
        return faqItems;
    }
}
