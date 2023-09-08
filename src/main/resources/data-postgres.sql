-- User
INSERT INTO users (id, shared_id, name, created_at, updated_at)
VALUES ('ab68ca39-e78f-4237-8cb2-11750ba4d8a8', 'ea857f9b-cf3a-4254-a6b6-fa99cf9f36bf', 'Volnei', '2023-09-01', '2023-09-01');

-- Card
INSERT INTO cards (id, number, holder_name, type, closing_day, user_id)
VALUES ('cf6ddc94-7e4c-4eec-9fc5-2d2974323998', '4444333322221111', 'Volnei Neves', 'GOLD', 1, 'ab68ca39-e78f-4237-8cb2-11750ba4d8a8');

-- Purchases
INSERT INTO purchases (id, amount, purchase_status, purchase_date, store, card_id)
VALUES ('35ea5466-057a-45ba-9be4-470c1306c366', 200.00, 'APPROVED', '2023-08-02', 'Generic Store', 'cf6ddc94-7e4c-4eec-9fc5-2d2974323998');

INSERT INTO purchases (id, amount, purchase_status, purchase_date, store, card_id)
VALUES ('2ea8424d-2abc-4321-a1cb-b0c4c582baa3', 150.00, 'APPROVED', '2023-08-31', 'Generic Store', 'cf6ddc94-7e4c-4eec-9fc5-2d2974323998');

INSERT INTO purchases (id, amount, purchase_status, purchase_date, store, card_id)
VALUES ('81025c3d-34e5-4d1d-a1f3-2d5a1c0f7f52', 125.00, 'DENIED', '2023-08-30', 'Generic Store', 'cf6ddc94-7e4c-4eec-9fc5-2d2974323998');
