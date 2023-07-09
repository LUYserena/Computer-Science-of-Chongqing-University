`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2021/11/20 21:12:20
// Design Name: 
// Module Name: dual_asy
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////



module dual_asy(clk,we1,oe1,addr1,d_in1,d_out1,we2,oe2,addr2,d_in2,d_out2);
    parameter DATA_WIDTH = 2;
    parameter ADDR_WIDTH = 4;
    parameter DEPTH = 1 << ADDR_WIDTH;

    //input ports
    input clk;
    //1
    input we1,oe1;
    input [ADDR_WIDTH-1:0] addr1;
    input [DATA_WIDTH-1:0] d_in1;
    output reg [DATA_WIDTH-1:0] d_out1;

    //2
    input we2,oe2;
    input [ADDR_WIDTH-1:0] addr2;
    input [DATA_WIDTH-1:0] d_in2;
    output reg [DATA_WIDTH-1:0] d_out2;

    //memory
    reg [DATA_WIDTH-1:0] mem[0:DEPTH-1];

    //tube wire
   

    integer i;
    // synopsys_translate_off
    initial
    begin
        for (i = 0; i < DEPTH; i=i+1)
            mem[i] = 8'h00;
    end
    //synopsys_translate_on

    //write block 1
    always@(posedge clk)
    begin
        if (we1)
            mem[addr1] <= d_in1;
    end

    //write block 2
    always@(posedge clk)
    begin
        if (we2)
        begin
            if (addr1 != addr2)
                mem[addr2] <= d_in2;
        end
    end

    //read1
    always@(posedge clk)
    begin
        if (!we1&&oe1)
            d_out1 <= mem[addr1];
        else
            d_out1 <= 0;
    end

    //read2
    always@(posedge clk)
    begin
        if (!we2&&oe2)
            d_out2 <= mem[addr2];
        else
            d_out2 <= 0;
    end
endmodule


