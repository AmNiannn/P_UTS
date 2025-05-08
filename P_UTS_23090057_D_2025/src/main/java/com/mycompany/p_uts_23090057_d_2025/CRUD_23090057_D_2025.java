package com.mycompany.p_uts_23090057_d_2025;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Scanner;

public class CRUD_23090057_D_2025 {
    public static void main(String[] args) {
        // Koneksi ke MongoDB lokal
        MongoClient client = MongoClients.create("mongodb://localhost:27017");

        // Ambil database dan collection
        MongoDatabase db = client.getDatabase("P_UTS_23090057_D_2025");
        MongoCollection<Document> col = db.getCollection("coll_23090057_D_2025");

        // Insert data
        Document doc1 = new Document("nama", "Fajar").append("umur", 21);
        Document doc2 = new Document("nama", "Arip").append("jurusan", "Teknik Informatika").append("ipk", 2.1);
        Document alamat = new Document("kota", "Tegal").append("kode_pos", 52183);
        Document doc3 = new Document("nim", "23090057").append("alamat", alamat);

        col.insertOne(doc1);
        col.insertOne(doc2);
        col.insertOne(doc3);

        System.out.println("=== Dokumentelah ditambahkan ===");

        // Menampilkan seluruh data(fungsi voew)
        System.out.println("\n=== Seluruh Dokumen ===");
        FindIterable<Document> hasil = col.find();
        for (Document d : hasil) {
            System.out.println(d.toJson());
        }

        // Update data (ubah nama "Fajar" jadi "Fajar Zul Qornain")
        Bson filterUpdate = Filters.eq("nama", "Fajar");
        Bson updateData = Updates.set("nama", "Fajar Zul Qornain");
        col.updateOne(filterUpdate, updateData);
        System.out.println("\n=== Dokumen dengan nama 'Fajar' telah diperbarui menjadi 'Fajar Zul Qornain' ===");

        // Hapus data (hapus dokumen dengan nama "Budi")
        Bson filterDelete = Filters.eq("nama", "Arip");
        col.deleteOne(filterDelete);
        System.out.println("\n=== Dokumen dengan nama 'Arip' telah dihapus ===");

        // Cari data berdasarkan kata kunci dari input pengguna
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nMasukkan kata kunci pencarian: ");
        String keyword = scanner.nextLine();

        Bson searchFilter = Filters.or(
            Filters.regex("nama", keyword, "i"),
            Filters.regex("nim", keyword, "i"),
            Filters.regex("jurusan", keyword, "i"),
            Filters.regex("alamat.kota", keyword, "i")
        );

        FindIterable<Document> hasilCari = col.find(searchFilter);
        System.out.println("\n=== Hasil Pencarian ===");
        for (Document d : hasilCari) {
            System.out.println(d.toJson());
        }
    }
}
