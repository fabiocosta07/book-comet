package com.bookcomet.bookinventoryservice.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PDF")
public class PDFBook extends Book{
}
