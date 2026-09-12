package com.propapp.service;

import com.propapp.model.Listing;
import com.propapp.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataSeedRunner implements CommandLineRunner {

    @Autowired
    private ListingRepository listingRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only seed data if the listings table inside PostgreSQL is currently blank
        if (listingRepository.count() == 0) {
            System.out.println("🚀 Table empty. Seeding mock real estate listings into PostgreSQL...");

            Listing l1 = createMock("prop_101", "MLS_A", "101 Ocean Blvd", "Miami", "FL", 495000.0, 3, LocalDate.now().minusDays(1), "Luxury beach condo with great views and modern kitchen.");
            Listing l2 = createMock("prop_102", "MLS_B", "405 Coconut Way", "Miami", "FL", 620000.0, 4, LocalDate.now().minusDays(5), "Spacious family villa with a private pool and beautiful backyard.");
            Listing l3 = createMock("prop_103", "MLS_A", "789 Alpine Rd", "Denver", "CO", 430000.0, 2, LocalDate.now().minusDays(12), "Cozy mountain retreat close to town and hiking trails.");
            Listing l4 = createMock("prop_104", "MLS_C", "221 Pinecrest St", "Miami", "FL", 540000.0, 3, LocalDate.now().minusDays(15), "Charming house in a quiet suburban neighborhood with large garage.");
            Listing l5 = createMock("prop_105", "MLS_B", "888 Broadway Apt 4", "Denver", "CO", 750000.0, 2, LocalDate.now(), "Modern downtown loft with large windows and rooftop access.");
            Listing l6 = createMock("prop_106", "MLS_A", "555 Lone Star Pkwy", "Austin", "TX", 390000.0, 3, LocalDate.now().minusDays(2), "Beautiful ranch style home with upgraded open floor plan.");
            Listing l7 = createMock("prop_107", "MLS_C", "312 Congress Ave", "Austin", "TX", 850000.0, 1, LocalDate.now().minusDays(25), "High-end studio loft penthouse located right in the city center.");
            Listing l8 = createMock("prop_108", "MLS_B", "601 Sunshine Blvd", "Orlando", "FL", 320000.0, 3, LocalDate.now().minusDays(4), "Cute starter family home with fresh paint close to theme parks.");
            Listing l9 = createMock("prop_109", "MLS_A", "144 Lakeview Dr", "Orlando", "FL", 510000.0, 4, LocalDate.now().minusDays(8), "Stunning lakefront property with a dock and huge screened patio.");
            Listing l10 = createMock("prop_110", "MLS_C", "909 Mile High Way", "Denver", "CO", 580000.0, 3, LocalDate.now().minusDays(19), "Newly renovated brick bungalow with a finished basement.");

            listingRepository.saveAll(Arrays.asList(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10));
            System.out.println("✅ Data seeding complete! 10 diverse listings loaded safely.");
        }
    }

    private Listing createMock(String id, String source, String address, String city, String state, 
                               Double price, Integer beds, LocalDate date, String desc) {
        Listing listing = new Listing();
        listing.setId(id);
        listing.setSource(source);
        listing.setAddress(address);
        listing.setCity(city);
        listing.setState(state);
        listing.setPrice(price);
        listing.setBedrooms(beds);
        listing.setListedDate(date);
        listing.setStatus("active");
        listing.setDescription(desc);
        return listing;
    }
}
