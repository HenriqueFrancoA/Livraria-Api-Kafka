INSERT INTO public.Authors(id, name, biography) VALUES (1, 'Paulo Coelho', 'Paulo Coelho de Souza é um escritor, letrista, jornalista e compositor brasileiro. Ocupa a 21° cadeira da Academia Brasileira de Letras. O livro O Alquimista é considerado como um importante fenômeno literário do século XX, e já vendeu mais de 150 milhões de cópias, superando livros como Le Petit Prince.');

INSERT INTO public.Categories(id, name) VALUES (1, 'Romance');
INSERT INTO public.Categories(id, name) VALUES (2, 'Drama');
INSERT INTO public.Categories(id, name) VALUES (3, 'Terror');
INSERT INTO public.Categories(id, name) VALUES (4, 'Ficção');

INSERT INTO public.Publishers(id, name, country) VALUES (1, 'Editora Schwarcz S.A.', 'Brasil');

INSERT INTO public.Books(id, title, isbn, publication_date, price, stock_quantity, author, publisher, category) VALUES (1, 'O Alquimista', '978-85-8439-067-0', '1988-04-15', 65.8, 20, 1, 1, 2);