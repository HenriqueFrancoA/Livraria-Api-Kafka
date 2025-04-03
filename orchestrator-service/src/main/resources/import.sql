INSERT INTO public.permission(id, description) VALUES (1, 'ADMIN');
INSERT INTO public.permission(id, description) VALUES (2, 'GERENTE');
INSERT INTO public.permission(id, description) VALUES (3, 'VENDEDOR');

INSERT INTO public.users(id, user_name, full_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES (1, 'henrique', 'Henrique Franco', '80146384d5716b9032326e04c8b1b29aa645ed3de2fcc1ee4244728ca0ffc3bba3288e0304e832d6', true, true, true, true);
