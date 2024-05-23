package com.developer.medical;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.developer.medical.Model.InstructionModel;

import java.util.ArrayList;

public class HealthInstruction extends AppCompatActivity {

    ListView listViewHealthInfo;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_health_instruction);

        ArrayList<InstructionModel> items = new ArrayList<>();
        items.add(new InstructionModel("Fever", "Wash your hands frequently to prevent the spread of germs."));
        items.add(new InstructionModel("Wounds", "Clean wounds with soap and water, apply antiseptic, and cover with a bandage. Get medical help for serious injuries.."));
        items.add(new InstructionModel("Diet", "Consume a balanced diet rich in fruits and vegetables."));
        items.add(new InstructionModel("Exercise", "Engage in physical activity for at least 30 minutes a day."));
        items.add(new InstructionModel("Sleep", "Get 7-9 hours of sleep for optimal well-being."));
        items.add(new InstructionModel("Posture and Ergonomics", "Maintain good posture and use ergonomic furniture to prevent back pain."));
        items.add(new InstructionModel("Sun Protection", "Use sunscreen to protect your skin from harmful UV rays."));
        items.add(new InstructionModel("Stress Management", "Practice stress-reduction techniques like meditation."));
        items.add(new InstructionModel("Avoid Smoking", "Quit smoking to reduce the risk of respiratory and heart diseases."));
        items.add(new InstructionModel("Limit Alcohol", "Consume alcohol in moderation to protect your liver and overall health."));
        items.add(new InstructionModel("Hydration", " Drink plenty of water throughout the day."));
        items.add(new InstructionModel("Check-ups", "Schedule regular health check-ups with your doctor."));


        listViewHealthInfo = findViewById(R.id.listViewHealthInfo);
        back = findViewById(R.id.back);
        ArrayAdapter<InstructionModel> infoModel = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);


        ArrayAdapter<InstructionModel> adapter = new ArrayAdapter<InstructionModel>(HealthInstruction.this, android.R.layout.simple_list_item_2, android.R.id.text1, items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                InstructionModel item = getItem(position);
                TextView titleView = view.findViewById(android.R.id.text1);
                TextView subtitleView = view.findViewById(android.R.id.text2);
                titleView.setTextColor(getResources().getColor(R.color.black));
                subtitleView.setTextColor(getResources().getColor(R.color.black));
                titleView.setText(item.getTitle());
                subtitleView.setText(item.getDescription());
                return view;
            }
        };

        listViewHealthInfo.setAdapter(adapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}