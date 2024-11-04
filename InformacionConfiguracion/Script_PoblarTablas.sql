

INSERT INTO baseprueba.persona (nombre, genero, edad, identificacion, direccion, telefono)
VALUES
    ('Jose Lema', 'Masculino', 30, 'ID-001', 'Otavalo sn y principal', '098254785'),
    ('Marianela Montalvo', 'Femenino', 28, 'ID-002', 'Amazonas y NNUU', '097548965'),
    ('Juan Osorio', 'Masculino', 35, 'ID-003', '13 junio y Equinoccial', '098874587');

INSERT INTO baseprueba.cliente (password, estado, id_persona)
VALUES
    ('1234', TRUE, 1),
    ('5678', TRUE, 2),
    ('1245', TRUE, 3); 
    
    
INSERT INTO baseprueba.cuenta  (numero_cuenta, tipo_cuenta, saldo_inicial, estado, clienteid)
VALUES
    ('478758', 'Ahorros', 2000, TRUE, 1),
    ('225487', 'Corriente', 100, TRUE, 2),
    ('495878', 'Ahorros', 0, TRUE, 3),
    ('496825', 'Ahorros', 540, TRUE, 2);

INSERT INTO baseprueba.cuenta  (numero_cuenta, tipo_cuenta, saldo_inicial, estado, clienteid)
VALUES
    ('585545', 'Corriente', 1000, TRUE, 1);

    
INSERT INTO baseprueba.cuenta  (numero_cuenta, tipo_cuenta, saldo_inicial, estado, clienteid)
VALUES
    ('585545', 'Corriente', 1000, TRUE, 1); -- Jose Lema

INSERT INTO baseprueba.movimientos (fecha, tipo_movimiento, valor, saldo, numero_cuenta)
VALUES
    ('2024-11-04 08:00:00', 'Retiro', -575, 1425, '478758'),       -- Cuenta de Jose Lema
    ('2024-11-04 08:05:00', 'Depósito', 600, 700, '225487'),       -- Cuenta de Marianela Montalvo
    ('2024-11-04 08:10:00', 'Depósito', 150, 150, '495878'),       -- Cuenta de Juan Osorio
    ('2024-11-04 08:15:00', 'Retiro', -540, 0, '496825');          -- Cuenta de Marianela Montalvo

    
    