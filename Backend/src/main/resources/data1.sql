-- If we reload the application the data gets lost, so if we don't want this use this default data

insert into product(name, description, brand, price, category, release_date, available, quantity) values
('Tata Nexon', 'A company SUV with excellent safety features and performance', 'Tata Motors', 750000.00, 'Cars', '2024-01-15', true, 50),
('Maruti Suzuki Swift', 'A popular hatchback known for its fuel efficiency and reliability', 'Maruti Suzuki', 550000.00, 'Cars', '2024-02-01', true, 100),
('Mahindra Thar', 'A rugged off-road SUV with powerful engine and modern amenities', 'Mahindra', 1200000.00, 'Cars', '2024-04-01', true, 30),
('Hyundai Creta', 'A stylish SUV with advanced features and comfortable interior', 'Hyundai', 950000.00, 'Cars', '2024-03-01', true, 75);
