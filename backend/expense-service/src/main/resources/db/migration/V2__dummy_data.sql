-- medical_expense 테이블에 더미 데이터 삽입
INSERT INTO medical_expense (medical_expense_id, pet_id, user_id, disease_name, cost, hospital, visited_at, memo)
VALUES (999, 999, 999, '피부병', 50000, '행복동물병원', '2024-10-07', '피부크림');

-- purchase 테이블에 더미 데이터 삽입
INSERT INTO purchase (purchase_id, user_id, title, detail, purchased_at, quantity, cost, category)
VALUES (999, 999, '꿀벌옷', '짱짱 귀여운 옷', '2024-10-07', 1, 40000, '물품');