package com.historiaclinica.aph.ui.activities;


import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.historiaclinica.aph.R;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class Pdf extends AppCompatActivity {

   EditText n_auto, b1_date1, b1_date2;
   Button generarpdf;

   @Override

    protected void onCreate(Bundle saveInstanceState){

       super.onCreate(saveInstanceState);
       setContentView(R.layout.fragment_inicio);
       n_auto = findViewById(R.id.n_auto);
       b1_date1 = findViewById(R.id.b1_date1);
       b1_date2 = findViewById(R.id.b1_date2);
       generarpdf = findViewById(R.id.finalizar);


       

   }

   private void createPdf(String, String, String, String)throws FileNotFoundException {
      String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
      File file = new File(pdfPath, "mypdf.pdf");
      OutputStream outputStream = new FileOutputStream();

      PdfWriter writer = new PdfWriter(file);
      PdfDocument pdfDocument = new PdfDocument(writer);
      Document document = new  Document(pdfDocument);



      document.close();
      Toast.makeText(this, "Pdf Creado", Toast.LENGTH_LONG).show();
   }

}
