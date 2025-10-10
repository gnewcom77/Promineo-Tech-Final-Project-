SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE sauce_ingredient;
TRUNCATE TABLE sauce;
TRUNCATE TABLE ingredient;
TRUNCATE TABLE pepper;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO pepper (name, heat_level, notes)
VALUES ('Jalapeno', 'Medium: 2500 - 8000 SHU', 'Earthy, Grassy, Dry/Smoke for Chipotle');

INSERT INTO pepper (name, heat_level, notes)
VALUES ('Thai Chili', 'Hot: 50000 - 100000 SHU', 'Sweet, Numbing, My Favorite!');

INSERT INTO pepper (name, heat_level, notes)
VALUES ('Shishito', 'Mild: 50 - 100 SHU', 'Earthy, no heat, good for Sweet Sauces');

INSERT INTO pepper (name, heat_level, notes)
VALUES ('Cherry Bomb', 'Mild: 2500 - 5000 SHU', 'Bright, Fruity, Great for Relishes');

INSERT INTO pepper (name, heat_level, notes)
VALUES ('Bolivian Rainbow Pepper', 'Medium: 10000 - 30000 SHU', 'NO FLAVOR! Use for Heat');

INSERT INTO pepper (name, heat_level, notes)
VALUES ('Scotch Bonnet', 'Hot: 100000 - 350000 SHU', 'Sweet, Fruity, Great for Tropical dishes');

INSERT INTO ingredient (name, notes) VALUES
('Garlic', 'Minced/Roasted'),
('White Vinegar', 'Neutral Flavor'),
('Apple Cider Vinegar', 'Sweet, Less Acidic that White Vinegar'),
('Lemon Juice', 'Fresh if available'),
('Lime Juice', 'Fresh if available'),
('Salt', 'Diamond-Crystal prefered'),
('Honey', 'Adds sweetness and balance'),
('Onion', 'Caramelized/Raw'),
('Pineapple', 'Sweet & Tropical'),
('Lemongrass', 'Citrusy and Earthy');


INSERT INTO sauce (name, style, heat_level, notes, pepper_id) VALUES
('Jalapeno-Pineapple', 'Vinegar', 'Medium', 'Bright and tropical sweet heat', 1),
('Jalapeno-Tequila/Lime', 'Vinegar', 'Medium', 'Flavorful and Earthy, classic green sauce!', 1),
('Thai Lemon Ginger', 'Vinegar', 'Hot', 'My favorite! Lemon Gingery Goodness!', 2),
('Shishito Sweet', 'Vinegar', 'Mild', 'Looooooow heat, use LOTS or Bolivian Rainbow Peppers for adding Heat.', 3);
