package com.bookcomet.bookinventoryservice.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("EPUB")
public class EPUBBook extends Book{
}

