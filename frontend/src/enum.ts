export const TransactionTypes = {
  INPUT: 'Entrada',
  OUTPUT: 'Saída',
} as const;

export const TransactionCategories = {
  INPUT: ['SALARIO', 'OUTROS'],
  OUTPUT: ['ALUGUEL', 'COMPRAS', 'COMIDA', 'SAUDE', 'LAZER', 'SUPERMERCADO', 'IFOOD', 'UBER', 'OUTROS'],
} as const;
