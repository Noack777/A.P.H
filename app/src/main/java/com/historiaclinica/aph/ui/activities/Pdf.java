package com.historiaclinica.aph.ui.activities;


import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.inappmessaging.model.ImageData;
import com.historiaclinica.aph.R;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;


/*public class Pdf extends AppCompatActivity {

   @Override
   public void setContentView(View view) {
      super.setContentView(R.layout.fragment_inicio);
   }

   public void crearPDF(View v) throws DocumentException {
      Document documento = new Document();
      File file = crearDirectorio(NOMBRE_DOCUMENTO);
      FileOutputStream ficheroPdf = null;
      try {
         ficheroPdf = new FileOutputStream(file.getAbsolutePath());
         PdfWriter.getInstance(documento, ficheroPdf);
         documento.open();
         Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_aph);
         ByteArrayOutputStream stream = new ByteArrayOutputStream();
         bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
         Image imagen = Image.getInstance(stream.toByteArray());
         documento.add(imagen);
         Font fuente = FontFactory.getFont(FontFactory.COURIER, 20, Font.BOLD, Color.BLUE);
         documento.add(new Paragraph("Información de la Tripulación y Turno",fuente));
         documento.add(new Paragraph("---------------------------------"));
         documento.add(new Paragraph("dato1": " + n_auto.getDatos() + "| |" + contacto.getNumero() + "| |" + contacto.getTipo()));
         documento.add(new Paragraph("dato2: " + b1_date1.getDatos()+ "| |" + b1_date1.getNumero() + "| |" + b1_date1.getTipo()));
         Toast.makeText(this, "Los datos mostrados  en pantalla se han almacenado en el archivo " + file.getName().toString(), Toast.LENGTH_LONG).show();
      } catch (Exception ex)
      {
         System.out.println("Error al crear pdf de contactos telefónicos: " + ex.getMessage());
      }
      finally
      {
         documento.close();
      }
   }


// Método que devuelve un objeto File con el directorio creado junto con el archivo pdf:

   public File crearDirectorio(String nombreFichero){
      File ruta = devuelveRuta();
      File fichero = null;
      if (ruta != null)
      {
         fichero = new File(ruta, nombreFichero);
      }
      return fichero;
   }

// Método que devuelve un objeto File con la ruta del directorio donde se creará el archivo pdf:

   public File devuelveRuta() {
      File ruta = null;
      if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
         ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),NOMBRE_DIRECTORIO);

         if (ruta != null)
         {
            if (!ruta.mkdirs())
            {
               if (!ruta.exists())
               {
                  return null;
               }
            }
         }
      } else {
         Toast.makeText(this, "La ruta indicada no existe.", Toast.LENGTH_LONG).show();
      }
      return ruta;
   }

   }



}
*/